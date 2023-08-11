package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderMarket_300492 extends OrderMarket{
    private MemberID memberID;
    private InvestorType investorType;
    private InvestorID investorID;
    private InvestorName investorName;
    private TraderCode traderCode;
    private SettlPeriod settlPeriod;
    private SettlType settlType;
    private Memo memo;
    private SecondaryOrderID secondaryOrderID;
    private BidTransType bidTransType;
    private BidExecInstType bidExecInstType;
    private LowLimitPrice lowLimitPrice;
    private HighLimitPrice highLimitPrice;
    private MinQty minQty;
    private TradeDate tradeDate;


    public OrderMarket_300492() {
        super(300492);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.memberID = new MemberID(body.readCharSequence(MemberID.LENGTH,StandardCharsets.UTF_8).toString());
        this.investorType = new InvestorType(body.readCharSequence(InvestorType.LENGTH, StandardCharsets.UTF_8).toString());
        this.investorID = new InvestorID(body.readCharSequence(InvestorID.LENGTH, StandardCharsets.UTF_8).toString());
        this.investorName = new InvestorName(body.readCharSequence(InvestorName.LENGTH, StandardCharsets.UTF_8).toString());
        this.traderCode = new TraderCode(body.readCharSequence(TraderCode.LENGTH, StandardCharsets.UTF_8).toString());
        this.settlPeriod = new SettlPeriod(body.readUnsignedByte());
        this.settlType = new SettlType(body.readUnsignedShort());
        this.memo = new Memo(body.readCharSequence(Memo.LENGTH, StandardCharsets.UTF_8).toString());
        this.secondaryOrderID = new SecondaryOrderID(body.readCharSequence(SecondaryOrderID.LENGTH, StandardCharsets.UTF_8).toString());
        this.bidTransType = new BidTransType(body.readUnsignedShort());
        this.bidExecInstType = new BidExecInstType(body.readUnsignedShort());
        this.lowLimitPrice = new LowLimitPrice(body.readLong());
        this.highLimitPrice = new HighLimitPrice(body.readLong());
        this.minQty = new MinQty(body.readLong());
        this.tradeDate = new TradeDate(body.readUnsignedInt());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("债券现券交易业务（除竞买方式）报价及大额逐笔委托行情");
        super.toStrings();
        System.out.println("交易商代码:" + this.memberID.toString());
        System.out.println("交易主体类型:" + this.investorType.toString());
        System.out.println("交易主体代码:" + this.investorID.toString());
        System.out.println("客户名称:" + this.investorName.toString());
        System.out.println("交易员代码:" + this.traderCode.toString());
        System.out.println("结算周期:" + this.settlPeriod.getValue());
        System.out.println("结算方式:" + this.settlType.getValue());
        System.out.println("备注:" + this.memo.toString());
        System.out.println("竞买场次编号:" + this.secondaryOrderID.toString());
        System.out.println("竞买业务类别:" + this.bidTransType.getValue());
        System.out.println("竞买成交方式:" + this.bidExecInstType.getValue());
        System.out.println("价格下限:" + this.lowLimitPrice.getValue());

        System.out.println("价格上限:" + this.highLimitPrice.getValue());
        System.out.println("最低成交数量:" + this.minQty.getValue());
        System.out.println("交易日期:" + this.tradeDate.getValue());


        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300492(*)");
            addSameStatement(ps);
            ps.setObject(10, memberID.getValue());
            ps.setObject(11, investorType.getValue());
            ps.setObject(12, investorID.getValue());
            ps.setObject(13, investorName.getValue());
            ps.setObject(14, traderCode.getValue());
            ps.setObject(15, settlPeriod.getValue());
            ps.setObject(16, settlType.getValue());
            ps.setObject(17, memo.getValue());
            ps.setObject(18, secondaryOrderID.getValue());
            ps.setObject(19, bidTransType.getValue());
            ps.setObject(20, bidExecInstType.getValue());
            ps.setObject(21, lowLimitPrice.getValue());
            ps.setObject(22, highLimitPrice.getValue());
            ps.setObject(23, minQty.getValue());
            ps.setObject(24, tradeDate.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return MemberID.LENGTH + InvestorType.LENGTH + InvestorID.LENGTH
                + InvestorName.LENGTH + TraderCode.LENGTH + SettlPeriod.getLength()
                + SettlType.getLength() + Memo.LENGTH + SecondaryOrderID.LENGTH
                + BidTransType.getLength() + BidExecInstType.getLength()
                + LowLimitPrice.getLength() + HighLimitPrice.getLength()
                + MinQty.getLength() + TradeDate.getLength();

    }

}
