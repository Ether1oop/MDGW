package message;

import dataType.OrdType;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderMarket_300292 extends OrderMarket{

    private OrdType ordType;

    public OrderMarket_300292() {
        super(300292);
    }
    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.ordType = new OrdType(body.readCharSequence(OrdType.LENGTH, StandardCharsets.UTF_8).toString());

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("债券现券交易业务匹配成交逐笔委托行情");
        super.toStrings();
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300292(*)");
            addSameStatement(ps);
            ps.setObject(10, ordType.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return OrdType.LENGTH;
    }
}
