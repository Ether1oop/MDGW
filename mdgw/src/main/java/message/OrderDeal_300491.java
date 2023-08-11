package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderDeal_300491 extends OrderDeal{
    private SettlPeriod settlPeriod;
    private SettlType settlType;
    private SecondaryOrderID secondaryOrderID;
    private BidExecInstType bidExecInstType;
    private MarginPrice marginPrice;

    public OrderDeal_300491() {
        super(300491);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + getOwnLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(this.settlPeriod.toBytes()).put(this.settlType.toBytes())
                .put(this.secondaryOrderID.toBytes())
                .put(this.bidExecInstType.toBytes())
                .put(marginPrice.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.settlPeriod = new SettlPeriod(body.readUnsignedByte());
        this.settlType = new SettlType(body.readUnsignedShort());
        this.secondaryOrderID = new SecondaryOrderID(body.readCharSequence(SecondaryOrderID.LENGTH, StandardCharsets.UTF_8).toString());
        this.bidExecInstType = new BidExecInstType(body.readUnsignedShort());
        this.marginPrice = new MarginPrice(body.readLong());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("竞买报价逐笔成交行情");
        super.toStrings();
        System.out.println("结算周期：" + this.settlPeriod.getValue());
        System.out.println("结算方式:" + this.settlType.getValue());
        System.out.println("竞买场次编号:" + this.secondaryOrderID.toString());
        System.out.println("竞买成交方式:" + this.bidExecInstType.getValue());
        System.out.println("达成成交的边际:" + this.marginPrice.getValue());
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300491 (*)");
            addSameStatement(ps);
            ps.setObject(12, settlPeriod.getValue());
            ps.setObject(13, settlType.getValue());
            ps.setObject(14, secondaryOrderID.getValue());
            ps.setObject(15, bidExecInstType.getValue());
            ps.setObject(16, marginPrice.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOwnLength(){
        return SettlType.getLength() + SettlPeriod.getLength() + SecondaryOrderID.LENGTH + BidExecInstType.getLength() + MarginPrice.getLength();
    }
}
