package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderMarket_300392 extends OrderMarket{
    private QuoteID quoteID;
    private MemberID memberID;
    private InvestorType investorType;
    private InvestorID investorID;
    private InvestorName investorName;
    private TraderCode traderCode;
    private SettlPeriod settlPeriod;
    private SettlType settlType;
    private Memo memo;

    public OrderMarket_300392() {
        super(300392);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.quoteID = new QuoteID(body.readCharSequence(QuoteID.LENGTH, StandardCharsets.UTF_8).toString());
        this.memberID = new MemberID(body.readCharSequence(MemberID.LENGTH,StandardCharsets.UTF_8).toString());
        this.investorType = new InvestorType(body.readCharSequence(InvestorType.LENGTH, StandardCharsets.UTF_8).toString());
        this.investorID = new InvestorID(body.readCharSequence(InvestorID.LENGTH, StandardCharsets.UTF_8).toString());
        this.investorName = new InvestorName(body.readCharSequence(InvestorName.LENGTH, StandardCharsets.UTF_8).toString());
        this.traderCode = new TraderCode(body.readCharSequence(TraderCode.LENGTH, StandardCharsets.UTF_8).toString());
        this.settlPeriod = new SettlPeriod(body.readUnsignedByte());
        this.settlType = new SettlType(body.readUnsignedShort());
        this.memo = new Memo(body.readCharSequence(Memo.LENGTH, StandardCharsets.UTF_8).toString());

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("债券现券交易业务（除竞买方式）报价及大额逐笔委托行情");
        super.toStrings();
        System.out.println("报价消息编号:" + this.quoteID.toString());
        System.out.println("交易商代码:" + this.memberID.toString());
        System.out.println("交易主体类型:" + this.investorType.toString());
        System.out.println("交易主体代码:" + this.investorID.toString());
        System.out.println("客户名称:" + this.investorName.toString());
        System.out.println("交易员代码:" + this.traderCode.toString());
        System.out.println("结算周期:" + this.settlPeriod.getValue());
        System.out.println("结算方式:" + this.settlType.getValue());
        System.out.println("备注:" + this.memo.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300392(*)");
            addSameStatement(ps);
            ps.setObject(10, quoteID.getValue());
            ps.setObject(11, memberID.getValue());
            ps.setObject(12, investorType.getValue());
            ps.setObject(13, investorID.getValue());
            ps.setObject(14, investorName.getValue());
            ps.setObject(15, traderCode.getValue());
            ps.setObject(16, settlPeriod.getValue());
            ps.setObject(17, settlType.getValue());
            ps.setObject(18, memo.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return QuoteID.LENGTH + MemberID.LENGTH + InvestorType.LENGTH
                + InvestorID.LENGTH + InvestorName.LENGTH + TraderCode.LENGTH
                + SettlPeriod.getLength() + SettlType.getLength() + Memo.LENGTH;

    }

}
