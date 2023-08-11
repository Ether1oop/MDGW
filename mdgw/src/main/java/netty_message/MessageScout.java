package netty_message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import message.Market_300111;
import message.Message;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MessageScout extends SimpleChannelInboundHandler<Message> {

    private String topic;
    private KafkaProducer<String, String> producer;

    // 在链接的时候发送登录消息，在断开的时候进行重连


    public MessageScout(String topic, KafkaProducer<String, String> producer) {
        this.topic = topic;
        this.producer = producer;
//        this.producer.initTransactions();
//        this.producer.beginTransaction();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException {
//            try {
//                this.producer.send(new ProducerRecord<>(this.topic, new ObjectMapper().writeValueAsString(message)));
//            }catch (Exception e){
//                this.producer.abortTransaction();
//            }
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(message));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功！");
        MarketBootstrap.getInstance(null).login();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接失败!");
        MarketBootstrap.getInstance(null).reconnect();
        this.producer.close();
    }
}
