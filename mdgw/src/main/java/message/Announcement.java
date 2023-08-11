package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Getter
public class Announcement extends Message{

    private OrigTime origTime;
    private ChannelNo channelNo;
    private NewsID newsID;
    private Headline headline;
    private RawDataFormat rawDataFormat;
    private RawDataLength rawDataLength;
    private RawData rawData;

    public Announcement() {
        super(390012);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.newsID = new NewsID(body.readCharSequence(NewsID.LENGTH, StandardCharsets.UTF_8).toString());
        this.headline = new Headline(body.readCharSequence(Headline.LENGTH, StandardCharsets.UTF_8).toString());
        this.rawDataFormat = new RawDataFormat(body.readCharSequence(RawDataFormat.LENGTH, StandardCharsets.UTF_8).toString());
        this.rawDataLength = new RawDataLength(body.readUnsignedInt());
        this.rawData = new RawData(body.readCharSequence((int) this.rawDataLength.getValue(), StandardCharsets.UTF_8).toString(), (int) this.rawDataLength.getValue());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("公告消息");
        System.out.println("公告时间：" + this.origTime.getValue());
        System.out.println("频道代码：" + this.channelNo.getValue());
        System.out.println("唯一标识：" + this.newsID.toString());
        System.out.println("公告标题：" + this.headline.toString());
        System.out.println("二进制数据格式：" + this.rawDataFormat.toString());
        System.out.println("二进制数据长度：" + this.rawDataLength.getValue());
        System.out.println("二进制数据：" + this.rawData.toString());
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {

    }

}
