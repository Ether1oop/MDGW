package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Getter
public class ReTransmission extends Message{
    //重传消息
    private ResendType resendType;
    private ChannelNo channelNo;
    private ApplBegSeqNum applBegSeqNum;
    private ApplEndSeqNum applEndSeqNum;
    private NewsID newsID;
    private ResendStatus resendStatus;
    private RejectText rejectText;


    public ReTransmission() {
        super(390094);
    }

    public ReTransmission(ResendType resendType, ChannelNo channelNo, ApplBegSeqNum applBegSeqNum, ApplEndSeqNum applEndSeqNum, NewsID newsID, ResendStatus resendStatus, RejectText rejectText) {
        super(390094);
        this.resendType = resendType;
        this.channelNo = channelNo;
        this.applBegSeqNum = applBegSeqNum;
        this.applEndSeqNum = applEndSeqNum;
        this.newsID = newsID;
        this.resendStatus = resendStatus;
        this.rejectText = rejectText;
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = ResendType.getLength() + ChannelNo.getLength() + ApplBegSeqNum.getLength()
                + ApplEndSeqNum.getLength() + newsID.getLength() + ResendStatus.getLength()
                + rejectText.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(resendType.toBytes())
                .put(channelNo.toBytes())
                .put(applBegSeqNum.toBytes())
                .put(newsID.toBytes())
                .put(resendStatus.toBytes())
                .put(rejectText.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.resendType = new ResendType(body.readUnsignedByte());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.applBegSeqNum = new ApplBegSeqNum(body.readLong());
        this.applEndSeqNum = new ApplEndSeqNum(body.readLong());
        this.newsID = new NewsID(body.readCharSequence(NewsID.LENGTH, StandardCharsets.UTF_8).toString());
        this.resendStatus = new ResendStatus(body.readUnsignedByte());
        this.rejectText = new RejectText(body.readCharSequence(RejectText.LENGTH, StandardCharsets.UTF_8).toString());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("重传种类：" + this.resendType.toString());
        System.out.println("频道代码：" + this.channelNo.toString());
        System.out.println("起始序号:" + this.applBegSeqNum.toString());
        System.out.println("结束序号:" + this.applEndSeqNum.toString());
        System.out.println("公告唯一标识:" + this.newsID.toString());
        System.out.println("重传状态:" + this.resendStatus.toString());
        System.out.println("文本:" + this.rejectText.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {

    }


}
