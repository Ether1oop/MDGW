package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderMarket_300792 extends OrderMarket{
    private ExpirationDays expirationDays;
    private ExpirationType expirationType;

    public OrderMarket_300792() {
        super(300792);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.expirationDays = new ExpirationDays(body.readUnsignedShort());
        this.expirationType = new ExpirationType(body.readUnsignedByte());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("协议交易业务逐笔委托行情");
        super.toStrings();
        System.out.println("期限，单位为天数:" + this.expirationDays.getValue());
        System.out.println("期限类型:" + this.expirationType.getValue());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300792 (*)");
            addSameStatement(ps);
            ps.setObject(10, expirationDays.getValue());
            ps.setObject(11, expirationType.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return ExpirationDays.getLength() + ExpirationType.getLength();
    }

}
