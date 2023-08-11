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
public class Market_303711 extends MarketSnapShot{
    private NoMDEntries noMDEntries;
    private MDEntryType[] mdEntryType;
    private MDEntryPx[] mdEntryPx;
    private MDEntrySize[] mdEntrySize;


    public Market_303711() {
        super(303711);
    }


    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + getOwnLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
//        buffer.put(this.noMDEntries.toBytes())
//                .put(this.mdEntryType.toBytes())
//                .put(this.mdEntryPx.toBytes())
//                .put(this.mdEntrySize.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
//        body.readerIndex(super.getLength());
        super.read(body);
        this.noMDEntries = new NoMDEntries(body.readUnsignedInt());

        int temp = (int) this.noMDEntries.getValue();

        if(this.noMDEntries.getValue() > 0){
            this.mdEntryType = new MDEntryType[temp];
            this.mdEntryPx = new MDEntryPx[temp];
            this.mdEntrySize = new MDEntrySize[temp];
            for(int i = 0; i < this.noMDEntries.getValue(); i++){
                this.mdEntryType[i] = new MDEntryType(body.readCharSequence(MDEntryType.LENGTH, StandardCharsets.UTF_8).toString());
                this.mdEntryPx[i] = new MDEntryPx(body.readLong());
                this.mdEntrySize[i] = new MDEntrySize(body.readLong());
            }
        }


    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("盘后定价交易业务行情快照");
        super.toStrings();
        System.out.println("行情条目个数：" + this.noMDEntries.getValue());

        if(this.noMDEntries.getValue() > 0){
            for(int i = 0; i < this.noMDEntries.getValue(); i++){
                System.out.println("行情条目类别：" + this.mdEntryType[i].toString());
                System.out.println("价格：" + this.mdEntryPx[i].getValue());
                System.out.println("数量：" + this.mdEntrySize[i].getValue());
            }
        }


        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_303711 (*)");
            if(noMDEntries.getValue() > 0){
                for (int i = 0; i < noMDEntries.getValue(); i++) {
                    addSameObject(ps);
                    ps.setObject(11, noMDEntries.getValue());
                    ps.setObject(12, mdEntryType[i].getValue());
                    ps.setObject(13, mdEntryPx[i].getValue());
                    ps.setObject(14, mdEntrySize[i].getValue());
                    ps.addBatch();
                }
            }
            else {
                addSameObject(ps);
                ps.setObject(11, noMDEntries.getValue());
                ps.setNull(12, Types.VARCHAR);
                ps.setNull(13, Types.INTEGER);
                ps.setNull(14, Types.INTEGER);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return NoMDEntries.getLength() + MDEntryType.LENGTH + MDEntryPx.getLength() + MDEntrySize.getLength();
    }
}
