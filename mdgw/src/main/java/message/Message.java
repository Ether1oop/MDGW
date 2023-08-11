package message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import netty_message.MsgType;
import util.BinaryUtil;
import util.uint32;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.sql.Connection;

@Getter
@Setter
public abstract class Message implements Serializable {
    /* 消息头 */
    // 消息类型
    private uint32 MsgType;
    // 消息体长度
    private uint32 BodyLength;
    /* 消息尾 */
    private uint32 Checksum;

    protected abstract byte[] toBytes();
    protected abstract void read(ByteBuf body);
    protected abstract void toStrings();

    public abstract void writeDateBase(Connection connection);

    public Message(long msgType){
        this.MsgType = new uint32(msgType);
    }

    public void resumeMessageFrom(byte[] data){
        try {
            ByteBuf buffer = Unpooled.wrappedBuffer(data);
            int msgTypeCode = buffer.readInt();
            netty_message.MsgType msgTypeEnum = netty_message.MsgType.lookup(msgTypeCode)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown msg type"));
            this.MsgType = new uint32(msgTypeEnum.getCode());
            this.BodyLength = new uint32(buffer.readUnsignedInt());
            if((int) this.BodyLength.getValue() < 0){
                return;
            }else {
                byte[] body = new byte[(int) this.BodyLength.getValue()];
                buffer.readBytes(body);
                read(Unpooled.wrappedBuffer(body));
                this.Checksum = new uint32(buffer.readUnsignedInt());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void GenerateCheckSum(byte[] buff){
        long cks = 0;
        int idx = 0;
        // 这里减去4是因为我们多申请了消息尾的空间，但是这里只需要计算消息头+消息体的内容
        while(idx < buff.length - 4){
            cks += buff[idx++];
        }

        Checksum = new uint32(cks % 256);
    }

    public static long generateCheckSum(byte[] buff){
        long cks = 0;
        int idx = 0;
        // 这里减去4是因为我们多申请了消息尾的空间，但是这里只需要计算消息头+消息体的内容
        while(idx < buff.length){
            cks += buff[idx++];
        }

        return new uint32(cks % 256).getValue();
    }


    public byte[] toBinaryMessage(){
        byte[] body = toBytes();
        this.BodyLength = new uint32(body.length);
        ByteBuffer buffer = ByteBuffer.allocate(8 + body.length + 4);
        buffer.putInt(BinaryUtil.bytesToInt(MsgType.toBytes()));
        buffer.putInt(body.length);
        buffer.put(body);
        byte[] msgComplete = buffer.array();
        GenerateCheckSum(msgComplete);
        byte[] checksumByte = this.Checksum.toBytes();
        System.arraycopy(checksumByte, 0, msgComplete, msgComplete.length - 4, 4);
        return msgComplete;
    }
}
