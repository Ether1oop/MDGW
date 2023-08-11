package message;

import dataType.ConfirmID;
import dataType.ContactInfo;
import dataType.Contactor;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class OrderMarket_300592 extends OrderMarket{
    private ConfirmID confirmID;
    private Contactor contactor;
    private ContactInfo contactInfo;

    public OrderMarket_300592() {
        super(300592);
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        super.read(body);
        this.confirmID = new ConfirmID(body.readCharSequence(ConfirmID.LENGTH,StandardCharsets.UTF_8).toString());
        this.contactor = new Contactor(body.readCharSequence(Contactor.LENGTH, StandardCharsets.UTF_8).toString());
        this.contactInfo = new ContactInfo(body.readCharSequence(ContactInfo.LENGTH, StandardCharsets.UTF_8).toString());

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("协议交易业务逐笔委托行情");
        super.toStrings();
        System.out.println("定价行情约定号:" + this.confirmID.toString());
        System.out.println("联系人:" + this.contactor.toString());
        System.out.println("联系方式:" + this.contactInfo.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300592 (*)");
            addSameStatement(ps);
            ps.setObject(10, confirmID.getValue());
            ps.setObject(11, contactor.getValue());
            ps.setObject(12, contactInfo.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return ConfirmID.LENGTH + Contactor.LENGTH + ContactInfo.LENGTH;
    }
}
