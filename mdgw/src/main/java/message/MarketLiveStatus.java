package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import util.BinaryUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class MarketLiveStatus extends Message{
    // 数据生成时间
    private OrigTime origTime;
    // 频道代码
    private ChannelNo channelNo;
    // 市场代码
    private MarketID marketID;
    // 市场板块代码，预留
    private MarketSegmentID marketSegmentID;
    // 交易会话 ID
    private TradingSessionID tradingSessionID;
    // 交易会话子 ID
    private TradingSessionSubID tradingSessionSubID;
    // 交易会话状态，预留
    private TradSesStatus tradSesStatus;
    // 交易会话起始时间，预留
    private TradSesStartTime tradSesStartTime;
    // 交易会话结束时间，预留
    private TradSesEndTime tradSesEndTime;
    // 每日初始额度
    private ThresholdAmount thresholdAmount;
    // 日中剩余额度.额度不可用时，发布固定值 0.0000
    private PosAmt posAmt;
    // 额度状态
    private AmountStatus amountStatus;

    public  MarketLiveStatus(){
        super(390019);

    }

    public MarketLiveStatus(OrigTime origTime, ChannelNo channelNo, MarketID marketID, MarketSegmentID marketSegmentID, TradingSessionID tradingSessionID, TradingSessionSubID tradingSessionSubID, TradSesStatus tradSesStatus, TradSesStartTime tradSesStartTime, TradSesEndTime tradSesEndTime, ThresholdAmount thresholdAmount, PosAmt posAmt, AmountStatus amountStatus) {
        super(390019);
        this.origTime = origTime;
        this.channelNo = channelNo;
        this.marketID = marketID;
        this.marketSegmentID = marketSegmentID;
        this.tradingSessionID = tradingSessionID;
        this.tradingSessionSubID = tradingSessionSubID;
        this.tradSesStatus = tradSesStatus;
        this.tradSesStartTime = tradSesStartTime;
        this.tradSesEndTime = tradSesEndTime;
        this.thresholdAmount = thresholdAmount;
        this.posAmt = posAmt;
        this.amountStatus = amountStatus;
    }

    @Override
    protected byte[] toBytes() {
        byte[] market_id = this.marketID.toBytes();
        byte[] market_segment_id = this.marketSegmentID.toBytes();
        byte[] trading_session_id = this.tradingSessionID.toBytes();
        byte[] trading_session_sub_id = this.tradingSessionSubID.toBytes();
        long bodyLength = OrigTime.getLength() + ChannelNo.getLength() + MarketID.LENGTH + MarketSegmentID.LENGTH + TradingSessionID.LENGTH
                + TradingSessionSubID.LENGTH + TradSesStatus.getLength() + TradSesStartTime.getLength() + TradSesEndTime.getLength()
                + ThresholdAmount.getLength() + PosAmt.getLength() + AmountStatus.getLength();
        ByteBuffer buffer = ByteBuffer.allocate((int) bodyLength);
        buffer.put(BinaryUtil.longToBytes(origTime.getValue()))
                .put(channelNo.toBytes())
                .put(market_id)
                .put(market_segment_id)
                .put(trading_session_id)
                .put(trading_session_sub_id)
                .put(tradSesStatus.toBytes())
                .put(BinaryUtil.longToBytes(tradSesStartTime.getValue()))
                .put(BinaryUtil.longToBytes(tradSesEndTime.getValue()))
                .put(BinaryUtil.longToBytes(thresholdAmount.getValue()))
                .put(BinaryUtil.longToBytes(posAmt.getValue()))
                .put(amountStatus.toBytes());

        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.marketID = new MarketID(body.readCharSequence(MarketID.LENGTH, StandardCharsets.UTF_8).toString());
        this.marketSegmentID = new MarketSegmentID(body.readCharSequence(MarketSegmentID.LENGTH, StandardCharsets.UTF_8).toString());
        this.tradingSessionID = new TradingSessionID(body.readCharSequence(TradingSessionID.LENGTH, StandardCharsets.UTF_8).toString());
        this.tradingSessionSubID = new TradingSessionSubID(body.readCharSequence(TradingSessionSubID.LENGTH, StandardCharsets.UTF_8).toString());
        this.tradSesStatus = new TradSesStatus(body.readUnsignedShort());
        this.tradSesStartTime = new TradSesStartTime(body.readLong());
        this.tradSesEndTime = new TradSesEndTime(body.readLong());
        this.thresholdAmount = new ThresholdAmount(body.readLong());
        this.posAmt = new PosAmt(body.readLong());
        this.amountStatus = new AmountStatus(body.readUnsignedByte());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println(" 市场实时状态");
        System.out.println("数据生成时间:" + this.origTime.getValue());
        System.out.println("频道代码:" + this.channelNo.getValue());
        System.out.println("市场代码:" + this.marketID.toString());
        System.out.println("市场板块代码，预留:" + this.marketSegmentID.toString());
        System.out.println("交易会话 ID:" + this.tradingSessionID.toString());
        System.out.println("交易会话子 ID :" + this.tradingSessionSubID.toString());
        System.out.println("交易会话状态，预留:" + this.tradSesStatus.getValue());
        System.out.println("交易会话起始时间，预留:" + this.tradSesStartTime.getValue());
        System.out.println("交易会话结束时间，预留:" + this.tradSesEndTime.getValue());
        System.out.println("每日初始额度:" + this.thresholdAmount.getValue());
        System.out.println("日中剩余额度:" + this.posAmt.getValue());
        System.out.println("额度状态:" + this.amountStatus.getValue());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_390019 (*)");
            ps.setObject(1, origTime.toDateType());
            ps.setObject(2, channelNo.getValue());
            ps.setObject(3, marketID.getValue());
            ps.setObject(4, marketSegmentID.getValue());
            ps.setObject(5, tradingSessionID.getValue());
            ps.setObject(6, tradingSessionSubID.getValue());
            ps.setObject(7, tradSesStatus.getValue());
            ps.setObject(8, tradSesStartTime.toDateType());
            ps.setObject(9, tradSesEndTime.toDateType());
            ps.setObject(10, thresholdAmount.getValue());
            ps.setObject(11, posAmt.getValue());
            ps.setObject(12, amountStatus.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
