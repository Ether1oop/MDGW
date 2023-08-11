package message;

import dataType.SettlPeriod;
import dataType.SettlType;
import dataType.StockNum;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderDeal_300391 extends OrderDeal{
    private SettlPeriod settlPeriod;
    private SettlType settlType;

    public OrderDeal_300391() {
        super(300391);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + getOwnLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(this.settlPeriod.toBytes()).put(this.settlType.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
        super.read(body);
        this.settlPeriod = new SettlPeriod(body.readUnsignedByte());
        this.settlType = new SettlType(body.readUnsignedShort());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("债券现券交易业务（除竞买方式）报价及大额逐笔成交行情扩展");
        super.toStrings();
        System.out.println("结算周期：" + this.settlPeriod.getValue());
        System.out.println("结算方式:" + this.settlType.getValue());
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300391 (*)");
            addSameStatement(ps);
            ps.setObject(12, settlPeriod.getValue());
            ps.setObject(13, settlType.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOwnLength(){
        return SettlType.getLength() + SettlPeriod.getLength();
    }
}
