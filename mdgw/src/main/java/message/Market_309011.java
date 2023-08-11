package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Getter
public class Market_309011 extends MarketSnapShot{
    private NoMDEntries noMDEntries;
    private MDEntryType[] mdEntryType;
    private MDEntryPx[] mdEntryPx;

    public Market_309011() {
        super(309011);
    }



    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + NoMDEntries.getLength() + MDEntryType.LENGTH + MDEntryPx.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
//        buffer.put(noMDEntries.toBytes())
//                .put(mdEntryPx.toBytes())
//                .put(mdEntryPx.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
//        body.readerIndex(super.getLength());
        super.read(body);
        noMDEntries = new NoMDEntries(body.readUnsignedInt());

        int temp = (int) noMDEntries.getValue();

        if(this.noMDEntries.getValue() > 0){
            this.mdEntryPx = new MDEntryPx[temp];
            this.mdEntryType = new MDEntryType[temp];
            for(int i = 0; i < temp; i++){
                mdEntryType[i] = new MDEntryType(body.readCharSequence(MDEntryType.LENGTH, StandardCharsets.UTF_8).toString());
                mdEntryPx[i] = new MDEntryPx(body.readLong());
            }
        }

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("指数快照行情");
        super.toStrings();
        System.out.println("行情条目个数：" + noMDEntries.getValue());
        if(noMDEntries.getValue() > 0){
            for(int i = 0; i < noMDEntries.getValue(); i++){
                System.out.println("行情条目类别：" + mdEntryType[i].toString());
                System.out.println("价格：" + mdEntryPx[i].getValue());
            }
        }
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_309011 (*)");
            if(noMDEntries.getValue() > 0){
                for (int i = 0; i < noMDEntries.getValue(); i++) {
                    addSameObject(ps);
                    ps.setObject(11, noMDEntries.getValue());
                    ps.setObject(12, mdEntryType[i].getValue());
                    ps.setObject(13, mdEntryPx[i].getValue());
                    ps.addBatch();
                }
            }
            else {
                addSameObject(ps);
                ps.setObject(11, noMDEntries.getValue());
                ps.setNull(12, Types.VARCHAR);
                ps.setNull(13, Types.INTEGER);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return NoMDEntries.getLength() + MDEntryType.LENGTH + MDEntryPx.getLength();
    }
}
