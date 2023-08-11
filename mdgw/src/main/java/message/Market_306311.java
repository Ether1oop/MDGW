package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Getter
public class Market_306311 extends MarketSnapShot{


    // 拓展字段
    private NoMDEntries noMDEntries;
    private MDEntryType[] mdEntryType;
    private MDEntryPx[] mdEntryPx;
    private MDEntrySize[] mdEntrySize;
    private MDPriceLevel[] mdPriceLevel;
    private NoComplexEventTimes noComplexEventTimes;
    private ComplexEventStartTime complexEventStartTime;
    private ComplexEventEndTime complexEventEndTime;

    public Market_306311(){
        super(306311);

    }


    @Override
    protected byte[] toBytes() {
//        int bodyLength = super.getLength() + NoMDEntries.getLength() + mdEntryType.getLength() + MDEntryPx.getLength()
//                + MDEntrySize.getLength() + MDPriceLevel.getLength() + NoComplexEventTimes.getLength()
//                + ComplexEventStartTime.getLength() + ComplexEventEndTime.getLength();
//        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
//        buffer.put(super.toBytes())
//                .put(noMDEntries.toBytes())
//                .put(mdEntryType.toBytes())
//                .put(mdEntryPx.toBytes())
//                .put(mdEntrySize.toBytes())
//                .put(mdPriceLevel.toBytes())
//                .put(noComplexEventTimes.toBytes())
//                .put(complexEventStartTime.toBytes())
//                .put(complexEventEndTime.toBytes());

        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
//        body.readerIndex(super.getLength());
        super.read(body);
        noMDEntries = new NoMDEntries(body.readUnsignedInt());
        int temp = (int) noMDEntries.getValue();

        if(temp > 0){
            mdEntryType = new MDEntryType[temp];
            mdEntryPx = new MDEntryPx[temp];
            mdEntrySize = new MDEntrySize[temp];
            mdPriceLevel = new MDPriceLevel[temp];

            for(int i = 0; i < temp; i++){
                mdEntryType[i] = new MDEntryType(body.readCharSequence(MDEntryType.LENGTH, StandardCharsets.UTF_8).toString());
                mdEntryPx[i] = new MDEntryPx(body.readLong());
                mdEntrySize[i] = new MDEntrySize(body.readLong());
                mdPriceLevel[i] = new MDPriceLevel(body.readUnsignedShort());
            }
        }

        noComplexEventTimes = new NoComplexEventTimes(body.readUnsignedInt());

        if(noComplexEventTimes.getValue() > 0){
            complexEventStartTime = new ComplexEventStartTime(body.readLong());
            complexEventEndTime = new ComplexEventEndTime(body.readLong());


        }



    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("港股实时行情");
        super.toStrings();
        System.out.println("行情条目个数：" + noMDEntries.getValue());

        if(noMDEntries.getValue() > 0){
            for(int i = 0; i < noMDEntries.getValue(); i++){
                System.out.println("行情条目类别：" + mdEntryType[i].toString());
                System.out.println("价格：" + mdEntryPx[i].getValue());
                System.out.println("数量：" + mdEntrySize[i].getValue());
                System.out.println("买卖盘档位：" + mdPriceLevel[i].toString());
            }
        }

        System.out.println("VCM 冷静期个数:" + noComplexEventTimes.toString());
        if(noComplexEventTimes.getValue() > 0){
            System.out.println("冷静期开始时间:" + complexEventStartTime.toString());
            System.out.println("冷静期结束时间:" + complexEventEndTime.toString());
        }

        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_306311 (*)");
            if(noMDEntries.getValue() > 0){
                for (int i = 0; i < noMDEntries.getValue(); i++) {
                    addSameObject(ps);
                    ps.setObject(11, noMDEntries.getValue());
                    ps.setObject(12, mdEntryType[i].getValue());
                    ps.setObject(13, mdEntryPx[i].getValue());
                    ps.setObject(14, mdEntrySize[i].getValue());
                    ps.setObject(15, mdPriceLevel[i].getValue());

                    ps.setObject(16, noComplexEventTimes.getValue());
                    if(noComplexEventTimes.getValue() > 0){
                        ps.setObject(17, complexEventStartTime.toDateType());
                        ps.setObject(18,complexEventEndTime.toDateType());
                    }
                    else{
                        ps.setNull(17, Types.TIMESTAMP_WITH_TIMEZONE);
                        ps.setNull(18, Types.TIMESTAMP_WITH_TIMEZONE);
                    }

                    ps.addBatch();
                }
            }
            else {
                addSameObject(ps);
                ps.setObject(11, noMDEntries.getValue());
                ps.setNull(12, Types.VARCHAR);
                ps.setNull(13, Types.INTEGER);
                ps.setNull(14, Types.INTEGER);
                ps.setNull(15, Types.INTEGER);

                ps.setObject(16, noComplexEventTimes.getValue());
                if(noComplexEventTimes.getValue() > 0){
                    ps.setObject(17, complexEventStartTime.toDateType());
                    ps.setObject(18,complexEventEndTime.toDateType());
                }
                else{
                    ps.setNull(17, Types.TIMESTAMP_WITH_TIMEZONE);
                    ps.setNull(18, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return NoMDEntries.getLength() + MDEntryType.LENGTH + MDEntryPx.getLength()
                + MDEntrySize.getLength() + MDPriceLevel.getLength() + NoComplexEventTimes.getLength()
                + ComplexEventStartTime.getLength() + ComplexEventEndTime.getLength();

    }
}
