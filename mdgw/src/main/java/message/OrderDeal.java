package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public abstract class OrderDeal extends Message{

    private ChannelNo channelNo;
    private ApplSeqNum applSeqNum;
    private MDStreamID mdStreamID;
    private BidApplSeqNum bidApplSeqNum;
    private OfferApplSeqNum offerApplSeqNum;
    private SecurityID securityID;
    private SecurityIDSource securityIDSource;
    private LastPx lastPx;
    private LastQty lastQty;
    private ExecType execType;
    private TransactTime transactTime;

    public OrderDeal(long msgType) {
        super(msgType);
    }


    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.applSeqNum = new ApplSeqNum(body.readLong());
        this.mdStreamID = new MDStreamID(body.readCharSequence(MDStreamID.LENGTH, StandardCharsets.UTF_8).toString());
        this.bidApplSeqNum = new BidApplSeqNum(body.readLong());
        this.offerApplSeqNum = new OfferApplSeqNum(body.readLong());
        this.securityID = new SecurityID(body.readCharSequence(SecurityID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityIDSource = new SecurityIDSource(body.readCharSequence(SecurityIDSource.LENGTH, StandardCharsets.UTF_8).toString());
        this.lastPx = new LastPx(body.readLong());
        this.lastQty = new LastQty(body.readLong());
        this.execType = new ExecType(body.readCharSequence(ExecType.LENGTH, StandardCharsets.UTF_8).toString());
        this.transactTime = new TransactTime(body.readLong());
    }

    @Override
    protected void toStrings() {
        System.out.println("频道代码:" + this.channelNo.getValue());
        System.out.println("消息记录号:" + this.applSeqNum.getValue());
        System.out.println("行情类别:" + this.mdStreamID.toString());
        System.out.println("买方委托索引:" + this.bidApplSeqNum.getValue());
        System.out.println("卖方委托索引:" + this.offerApplSeqNum.getValue());
        System.out.println("证券代码:" + this.securityID.toString());
        System.out.println("证券代码源:" + this.securityIDSource.toString());
        System.out.println("成交价格:" + this.lastPx.getValue());
        System.out.println("成交数量:" + this.lastQty.getValue());
        System.out.println("成交类别:" + this.execType.toString());
        System.out.println("成交时间:" + this.transactTime.getValue());


    }

    protected void addSameStatement(PreparedStatement ps) throws SQLException {
        ps.setObject(1, channelNo.getValue());
        ps.setObject(2, applSeqNum.getValue());
        ps.setObject(3, mdStreamID.getValue());
        ps.setObject(4, bidApplSeqNum.getValue());
        ps.setObject(5, offerApplSeqNum.getValue());
        ps.setObject(6, securityID.getValue());
        ps.setObject(7, securityIDSource.getValue());
        ps.setObject(8, lastPx.getValue());
        ps.setObject(9, lastQty.getValue());
        ps.setObject(10, execType.getValue());
        ps.setObject(11, transactTime.toDateType());
    }

    public int getLength(){
        return ChannelNo.getLength() + ApplSeqNum.getLength() + MDStreamID.LENGTH + BidApplSeqNum.getLength() + OfferApplSeqNum.getLength()
                + SecurityID.LENGTH + SecurityIDSource.LENGTH + LastPx.getLength() + LastQty.getLength() + ExecType.LENGTH
                + TransactTime.getLength();
    }
}
