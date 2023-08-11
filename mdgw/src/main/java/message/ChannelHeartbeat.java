package message;

import dataType.ApplLastSeqNum;
import dataType.ChannelNo;
import dataType.EndOfChannel;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.sql.Connection;

@Getter
public class ChannelHeartbeat extends Message{
    //频道心跳
    private ChannelNo channelNo;
    private ApplLastSeqNum applLastSeqNum;
    private EndOfChannel endOfChannel;

    public ChannelHeartbeat() {
        super(390095);
    }

    public ChannelHeartbeat(ChannelNo channelNo, ApplLastSeqNum applLastSeqNum, EndOfChannel endOfChannel) {
        super(390095);
        this.channelNo = channelNo;
        this.applLastSeqNum = applLastSeqNum;
        this.endOfChannel = endOfChannel;
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = ChannelNo.getLength() + ApplLastSeqNum.getLength() + EndOfChannel.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(channelNo.toBytes())
                .putLong(applLastSeqNum.getValue())
                .put(endOfChannel.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.applLastSeqNum = new ApplLastSeqNum(body.readLong());
        this.endOfChannel = new EndOfChannel(body.readUnsignedShort());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("消息类型： 频道心跳");
        System.out.println("频道代码:" + channelNo.toString());
        System.out.println("最后一条行情信息的记录号:" + applLastSeqNum.toString());
        System.out.println("频道结束标志:" + endOfChannel.toString());
    }

    @Override
    public void writeDateBase(Connection connection) {

    }


}
