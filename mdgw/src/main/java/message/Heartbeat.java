package message;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.sql.Connection;

@Getter
public class Heartbeat extends Message{

    public Heartbeat(){
        super(3);
    }

    public Heartbeat(long msgType) {
        super(msgType);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("心跳消息");
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {

    }


    //MsgType = 3, BodyLength=0
//    private MsgHeader StandardHeader;


}
