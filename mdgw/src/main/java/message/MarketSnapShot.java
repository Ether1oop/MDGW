package message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import serializer.stringByteDeserializer;
import serializer.stringByteSerializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public abstract class MarketSnapShot extends Message{

    public OrigTime origTime;
    public ChannelNo channelNo;
    @JsonSerialize(using = stringByteSerializer.class)
    public MDStreamID mdStreamID;
    @JsonSerialize(using = stringByteSerializer.class)
    public SecurityID securityID;
    @JsonSerialize(using = stringByteSerializer.class)
    public SecurityIDSource securityIDSource;
    @JsonSerialize(using = stringByteSerializer.class)
    public TradingPhaseCode tradingPhaseCode;
    public PrevClosePx prevClosePx;
    public NumTrades numTrades;
    public TotalVolumeTrade totalVolumeTrade;
    public TotalValueTrade totalValueTrade;

    public MarketSnapShot(long msgType){
        super(msgType);
    }

    public int getLength(){
        return 0;
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(origTime.toBytes())
                .put(channelNo.toBytes())
                .put(mdStreamID.toBytes())
                .put(securityID.toBytes())
                .put(securityIDSource.toBytes())
                .put(tradingPhaseCode.toBytes())
                .put(prevClosePx.toBytes())
                .put(numTrades.toBytes())
                .put(totalVolumeTrade.toBytes())
                .put(totalValueTrade.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.mdStreamID = new MDStreamID(body.readCharSequence(MDStreamID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityID = new SecurityID(body.readCharSequence(SecurityID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityIDSource = new SecurityIDSource(body.readCharSequence(SecurityIDSource.LENGTH, StandardCharsets.UTF_8).toString());
        this.tradingPhaseCode = new TradingPhaseCode(body.readCharSequence(TradingPhaseCode.LENGTH, StandardCharsets.UTF_8).toString());
        this.prevClosePx = new PrevClosePx(body.readLong());
        this.numTrades = new NumTrades(body.readLong());
        this.totalVolumeTrade = new TotalVolumeTrade(body.readLong());
        this.totalValueTrade = new TotalValueTrade(body.readLong());
    }

    @Override
    protected void toStrings() {
        System.out.println("数据生成时间：" + origTime.toString());
        System.out.println("频道代码：" + channelNo.toString());
        System.out.println("行情类别:" + mdStreamID.toString());
        System.out.println("证券代码:" + securityID.toString());
        System.out.println("证券代码源:" + securityIDSource.toString());
        System.out.println("产品所处的交易阶段代码:" + tradingPhaseCode.toString());
        System.out.println("昨收价:" + prevClosePx.toString());
        System.out.println("成交笔数:" + numTrades.toString());
        System.out.println("成交总量:" + totalVolumeTrade.toString());
        System.out.println("成交总金额:" + totalValueTrade.toString());
    }

    public void addSameObject(PreparedStatement ps) throws SQLException {
        ps.setObject(1, origTime.toDateType());
        ps.setObject(2, channelNo.getValue());
        ps.setObject(3, mdStreamID.getValue());
        ps.setObject(4, securityID.getValue());
        ps.setObject(5, securityIDSource.getValue());
        ps.setObject(6, tradingPhaseCode.getValue());
        ps.setObject(7, prevClosePx.getValue());
        ps.setObject(8, numTrades.getValue());
        ps.setObject(9, totalVolumeTrade.getValue());
        ps.setObject(10, totalValueTrade.getValue());
    }


//    public String getMdStreamID() {
//        return mdStreamID.toString();
//    }
//
//    public String getSecurityID() {
//        return securityID.toString();
//    }
//
//    public String getSecurityIDSource() {
//        return securityIDSource.toString();
//    }
//
//    public String getTradingPhaseCode() {
//        return tradingPhaseCode.toString();
//    }


}
