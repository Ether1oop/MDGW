package netty_message;

import configs.sysconfig;
import database.connectEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import message.Logon;
import message.Logout;
import message.Market_300111;
import message.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import serializer.MsgpackSerializer;

import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class MarketBootstrap {

    private static volatile MarketBootstrap INSTANCE;

    private Bootstrap bootstrap;
    private sysconfig sysconfig;
    private Channel channel;
    private volatile boolean isRunning;
    private int reconnectCount = 0;
    private connectEntity connect_entity;
    private KafkaProducer<String, String> producer;

    public static MarketBootstrap getInstance(sysconfig config) throws UnknownHostException {
        if(INSTANCE == null){
            synchronized (MarketBootstrap.class){
                if(INSTANCE == null){
                    if(config == null){
                        INSTANCE = new MarketBootstrap();
                    }else {
                        INSTANCE = new MarketBootstrap(config);
                    }
                }
            }
        }
        return INSTANCE;
    }

    public MarketBootstrap(sysconfig config){
        sysconfig = config;
        connect_entity = new connectEntity(sysconfig.getDriverName(),sysconfig.getUrl(),sysconfig.getUser(),sysconfig.getPassword());
    }

    public MarketBootstrap(sysconfig config, KafkaProducer<String, String> kafkaProducer){
        sysconfig = config;
        connect_entity = new connectEntity(sysconfig.getDriverName(),sysconfig.getUrl(),sysconfig.getUser(),sysconfig.getPassword());
        this.producer = kafkaProducer;
    }

    public MarketBootstrap() throws UnknownHostException {
        sysconfig = new sysconfig();
        connect_entity = new connectEntity(sysconfig.getDriverName(),sysconfig.getUrl(),sysconfig.getUser(),sysconfig.getPassword());
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, sysconfig.getBroker());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MsgpackSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.RoundRobinPartitioner");
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction");
        properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 600000);
        this.producer = new KafkaProducer<>(properties);
    }

    public boolean connect(){
        Connection db_connection = this.connect_entity.connection();
        if(!isRunning && db_connection != null){
            NioEventLoopGroup workerGroup = new NioEventLoopGroup(10);
            this.bootstrap = new Bootstrap();
            this.bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline()
                                    // 心跳消息
                                    .addLast(new ReadIdleStateHandler(0, sysconfig.getHeartbeatInterval(), 0))
                                    // 自定义长度解决TCP粘包和黏包问题
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4, 4, 0))
                                    // 对消息进行解码
                                    .addLast(new MessageDecoder())
                                    // 对消息进行存储
                                    .addLast(new MessageStore(db_connection))
                                    // 对消息进行分发
                                    .addLast(new MessageScout(sysconfig.getTopic(), producer))
                                    // 对消息进行编码
                                    .addLast(new MessageEncoder());
                        }
                    });

            try {
                ChannelFuture future = bootstrap.connect(sysconfig.getHost(), sysconfig.getPort()).sync();
                if(future.isSuccess()){
                    channel = future.channel();
                    this.isRunning = true;
                    System.out.println("connect to server success:" + sysconfig.getHost() + ":" + sysconfig.getPort());
                }else {
                    reconnect();
                    System.out.println("connect to server failure:" + sysconfig.getHost() + ":" + sysconfig.getPort());
                }
            } catch (InterruptedException e) {
                System.out.println("connecting error!");
                e.printStackTrace();
                reconnect();
            }
        }

        return this.isRunning;
    }

    public synchronized void reconnect(){
        disconnect();
        System.out.println("第" + String.valueOf(reconnectCount) +"次开始重连...");
        reconnectCount++;
        ChannelFuture future = bootstrap.connect(sysconfig.getHost(), sysconfig.getPort());
        future.addListener((ChannelFuture f) -> {
            if(!f.isSuccess()){
                f.channel().eventLoop().schedule(this::reconnect, sysconfig.getReconnectInterval(), TimeUnit.SECONDS);

            }else {
                reconnectCount = 1;
                channel = f.channel();
            }
        });
    }

    public synchronized void disconnect(){
        if(channel != null && channel.isActive()){
            channel.close();
        }
    }

    public boolean isConnect(){
        return isRunning && channel != null && channel.isOpen() && channel.isActive();
    }

    public void sendMessage(Message message){
        if(isConnect()){

            channel.writeAndFlush(message);
        }else{
            System.out.println("can not send because connect is close");
        }
    }

    public void login(){
        Logon logon = new Logon(
                sysconfig.getSenderCompId(),
                sysconfig.getTargetCompId(),
                sysconfig.getHeartbeatInterval(),
                sysconfig.getPassword(),
                sysconfig.getVersion()
        );
        sendMessage(logon);
        System.out.println("send login to server: " + sysconfig.getHost() + ":" + sysconfig.getPort());
    }

    public void logout(){
        Logout logout = new Logout();
        sendMessage(logout);
        channel.close();
        System.out.println("send logout to server: " + sysconfig.getHost() + ":" + sysconfig.getPort());

    }

}
