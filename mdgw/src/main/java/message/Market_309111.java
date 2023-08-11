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
public class Market_309111 extends MarketSnapShot{
    private StockNum stockNum;


    public Market_309111() {
        super(309111);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + getOwnLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(this.stockNum.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
        super.read(body);
        this.stockNum = new StockNum(body.readUnsignedInt());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("成交量统计指标行情快照");
        super.toStrings();
        System.out.println("统计量指标样本个数：" + stockNum.getValue());
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_309111 (*)");
            addSameObject(ps);
            ps.setObject(11, stockNum.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return StockNum.getLength();
    }
}
