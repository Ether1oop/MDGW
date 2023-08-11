package message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import serializer.stringByteArrayDeserializer;
import serializer.stringByteArraySerializer;
import serializer.stringByteSerializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Market_300111 extends MarketSnapShot{
    private NoMDEntries noMDEntries;
    @JsonSerialize(using = stringByteArraySerializer.class)
//    @JsonDeserialize(using = stringByteArrayDeserializer.class)
    private MDEntryType[] mdEntryType;
    private MDEntryPx[] mdEntryPx;
    private MDEntrySize[] mdEntrySize;
    private MDPriceLevel[] mdPriceLevel;
    private NumberOfOrders[] numberOfOrders;
    private NoOrders[] noOrders;
    private OrderQty[][] orderQty;

    public Market_300111() {
        super(300111);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() ;
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
//        super.read(body.slice(0, super.getLength()));
//        ByteBuf buf = body.slice(super.getLength(), getOwnLength());
//        body.readerIndex(super.getLength());
        super.read(body);
        noMDEntries = new NoMDEntries(body.readUnsignedInt());
        int temp = (int) this.noMDEntries.getValue();
        if(this.noMDEntries.getValue() > 0){
            this.mdEntryType = new MDEntryType[temp];
            this.mdEntryPx = new MDEntryPx[temp];
            this.mdEntrySize = new MDEntrySize[temp];
            this.mdPriceLevel = new MDPriceLevel[temp];
            this.numberOfOrders = new NumberOfOrders[temp];
            this.noOrders = new NoOrders[temp];
            this.orderQty = new OrderQty[temp][];

            for(int i = 0; i < temp; i++){
                mdEntryType[i] = new MDEntryType(body.readCharSequence(MDEntryType.LENGTH, StandardCharsets.UTF_8).toString());
                mdEntryPx[i] = new MDEntryPx(body.readLong());
//                if(this.mdEntryPx[i].getValue() < 0){
//                    System.out.println("s");
//                }
                mdEntrySize[i] = new MDEntrySize(body.readLong());
                mdPriceLevel[i] = new MDPriceLevel(body.readUnsignedShort());
                numberOfOrders[i] = new NumberOfOrders(body.readLong());
                noOrders[i] = new NoOrders(body.readUnsignedInt());

                int num = (int) this.noOrders[i].getValue();
                if(num > 0){
                    orderQty[i] = new OrderQty[num];

                    for (int j = 0; j < num; j++){
                        orderQty[i][j] = new OrderQty(body.readLong());
                    }
                }
            }
        }

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("现货（股票，基金等）集中竞价交易快照行情");
        super.toStrings();
        System.out.println("行情条目个数：" + noMDEntries.getValue());
        if(this.noMDEntries.getValue() > 0){
            for(int i = 0; i < this.noMDEntries.getValue(); i++){
                System.out.println("###########");
                System.out.println("行情条目类别：" + mdEntryType[i].toString());
                System.out.println("价格：" + mdEntryPx[i].getValue());
                System.out.println("数量：" + mdEntrySize[i].getValue());
                System.out.println("买卖盘档位:" + mdPriceLevel[i].getValue());
                System.out.println("价位总委托笔数:" + numberOfOrders[i].getValue());
                System.out.println("价位揭示委托笔数:" + noOrders[i].getValue());

                if(noOrders[i].getValue() > 0){
                    for (int j = 0; j < noOrders[i].getValue(); j++){
                        System.out.println("委托数量:" + orderQty[i][j].getValue());
                    }
                }
            }
        }
        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300111 (*)");
            if(noMDEntries.getValue() > 0){
                for(int i = 0; i < noMDEntries.getValue(); i++){
                    if(noOrders[i].getValue() > 0){
                        for(int j = 0; j < noOrders[i].getValue(); j++){
                            addSameObject(ps);

                            ps.setObject(11, noMDEntries.getValue());
                            ps.setObject(12, mdEntryType[i].getValue());
                            ps.setObject(13, mdEntryPx[i].getValue());
                            ps.setObject(14, mdEntrySize[i].getValue());
                            ps.setObject(15, mdPriceLevel[i].getValue());
                            ps.setObject(16, numberOfOrders[i].getValue());
                            ps.setObject(17, noOrders[i].getValue());
                            ps.setObject(18, orderQty[i][j].getValue());
                            ps.addBatch();
                        }
                    }
                    else {
                        addSameObject(ps);

                        ps.setObject(11, noMDEntries.getValue());
                        ps.setObject(12, mdEntryType[i].getValue());
                        ps.setObject(13, mdEntryPx[i].getValue());
                        ps.setObject(14, mdEntrySize[i].getValue());
                        ps.setObject(15, mdPriceLevel[i].getValue());
                        ps.setObject(16, numberOfOrders[i].getValue());
                        ps.setObject(17, noOrders[i].getValue());
                        ps.setNull(18, Types.INTEGER);
                        ps.addBatch();
                    }
                }
            }
            else {
                addSameObject(ps);

                ps.setObject(11, noMDEntries.getValue());
                ps.setNull(12, Types.VARCHAR);
                ps.setNull(13, Types.INTEGER);
                ps.setNull(14, Types.INTEGER);
                ps.setNull(15, Types.INTEGER);
                ps.setNull(16, Types.INTEGER);
                ps.setNull(17, Types.INTEGER);
                ps.setNull(18, Types.INTEGER);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
