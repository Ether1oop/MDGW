package session;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import message.Message;
import message.parse;
import util.BinaryUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class mdgwSession {
    private SocketChannel socketChannel;
    private Socket socket;

    // 使用SocketChannel发起TCP链接
    public boolean LinkWithTCP(String ip, int port) throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
        this.socketChannel.configureBlocking(true);
//        System.out.println(socketChannel.isConnected());
        return this.socketChannel.isConnected();
    }

    // 使用SocketChannel发送消息
    public void sendMessageBySocketChannel(byte[] message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(message.length);
        buffer.clear();
        buffer.put(message);
        buffer.flip();
        while(buffer.hasRemaining()){
            this.socketChannel.write(buffer);
        }
    }

    // 使用SocketChannel读取消息
    public long receiveMessageBySocketChannel() throws IOException{
        ByteBuffer message_header = ByteBuffer.allocate(4);
        int temp = this.socketChannel.read(message_header);
        long msgType = BinaryUtil.bytesToUint(message_header.array());
        if(parse.isMessage(msgType) != -1){
            message_header.clear();
            // 根据消息体长度获取消息体
            temp = this.socketChannel.read(message_header);
            long bodyLength = BinaryUtil.bytesToUint(message_header.array());

            if (bodyLength <= 0 || (int) bodyLength < 0){
//                System.out.println(bodyLength);
                return -1;
            }

            ByteBuffer message_body = ByteBuffer.allocate((int) bodyLength);
            temp = this.socketChannel.read(message_body);

            // 根据消息长度获取消息尾
            ByteBuffer message_tail = ByteBuffer.allocate(4);
            temp = this.socketChannel.read(message_tail);

            System.out.println(msgType + "\t" + bodyLength);

            // 校验消息尾
            ByteBuffer buffer;
            try {
                buffer = ByteBuffer.allocate((int) (8 + bodyLength));

            }catch (OutOfMemoryError e){
                return -1;
            }
            buffer.put(BinaryUtil.uintToBytes(msgType))
                    .put(BinaryUtil.uintToBytes(bodyLength))
                    .put(message_body.array());
            if(BinaryUtil.bytesToUint(message_tail.array()) == Message.generateCheckSum(buffer.array())){
                ByteBuf buf = Unpooled.wrappedBuffer(message_body.array());
                parse.parseMessage(msgType, buf);
            }



        }



//        // 先读取消息头
//        ByteBuffer message_header = ByteBuffer.allocate(8);
//        int temp = this.socketChannel.read(message_header);
//        long msgType = BinaryUtil.bytesToUint(message_header.array(), 0);
//        long bodyLength = BinaryUtil.bytesToUint(message_header.array(), 4);
//        // 根据消息体长度获取消息体
//        System.out.println(String.valueOf(msgType) + "\t\t" + String.valueOf(bodyLength));
//        ByteBuffer message_body = ByteBuffer.allocate((int) bodyLength);
//        temp = this.socketChannel.read(message_body);
//        // 根据消息长度获取消息尾
//        ByteBuffer message_tail = ByteBuffer.allocate(4);
//        temp = this.socketChannel.read(message_tail);
        // 校验消息尾
//        ByteBuffer buffer = ByteBuffer.allocate((int) (8 + bodyLength));
//        buffer.put(message_header.array()).put(message_body.array());
//
//        if(BinaryUtil.bytesToUint(message_tail.array()) == Message.generateCheckSum(buffer.array())){
//            ByteBuf buf = Unpooled.wrappedBuffer(message_body.array());
//            parse.parseMessage(msgType, buf);
//        }else{
//            System.out.println(" ");
//            ByteBuf buf = Unpooled.wrappedBuffer(message_body.array());
//            parse.parseMessage(msgType, buf);
//        }

        // 解析消息体
        return msgType;
    }

    // 发起TCP链接
    public boolean LoginMdgw(String ip, int port){
        try {
            this.socket = new Socket(ip, port);
            System.out.println("MDGW链接成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 发送消息
    public int sendMessage(byte[] message) throws IOException {
        DataOutputStream client_input = new DataOutputStream(socket.getOutputStream());
        client_input.write(message);
        client_input.flush();

        return 1;
    }

    // 接收消息
    public void receiveMessage() throws IOException {
        DataInputStream client_output = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream("result.out"));

        byte[] buffer = new byte[4096];
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        // count 表示接收了的数据大小，如果为0，则说明接收完毕。
        while((count = client_output.read(buffer)) != -1){
//            out.write(buffer, 0, count);
//            stringBuilder.append(new String(buffer, 0, count));
            System.out.println(new String(buffer, 0, count));
        }
        out.close();
//        client_output.close();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public Socket getSocket() {
        return socket;
    }
}
