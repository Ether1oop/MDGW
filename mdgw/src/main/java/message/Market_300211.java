package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Getter
public class Market_300211 extends MarketSnapShot{
    private NoMDEntries noMDEntries;
    private MDEntryType[] mdEntryType;
    private MDEntryPx[] mdEntryPx;
    private MDEntrySize[] mdEntrySize;
    private MDPriceLevel[] mdPriceLevel;
    private NumberOfOrders[] numberOfOrders;
    private NoOrders[] noOrders;
    private OrderQty[][] orderQty;

    private NoSubTradingPhaseCodes noSubTradingPhaseCodes;
    private SubTradingPhaseCode[] subTradingPhaseCode;
    private TradingType[] tradingType;
    private AuctionVolumeTrade auctionVolumeTrade;
    private AuctionValueTrade auctionValueTrade;

    public Market_300211() {
        super(300211);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = super.getLength() + getOwnLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        try {
            super.read(body);
            this.noMDEntries = new NoMDEntries(body.readUnsignedInt());
            int temp = (int) this.noMDEntries.getValue();
            if(this.noMDEntries.getValue() > 0){
                this.mdEntryType = new MDEntryType[temp];
                this.mdEntryPx = new MDEntryPx[temp];
                this.mdEntrySize = new MDEntrySize[temp];
                this.mdPriceLevel = new MDPriceLevel[temp];
                this.numberOfOrders = new NumberOfOrders[temp];
                this.noOrders = new NoOrders[temp];
                this.orderQty = new OrderQty[temp][];

                for(int i = 0; i < this.noMDEntries.getValue(); i++){
                    this.mdEntryType[i] = new MDEntryType(body.readCharSequence(MDEntryType.LENGTH, StandardCharsets.UTF_8).toString());
                    this.mdEntryPx[i] = new MDEntryPx(body.readLong());
                    this.mdEntrySize[i] = new MDEntrySize(body.readLong());
                    this.mdPriceLevel[i] = new MDPriceLevel(body.readUnsignedShort());
                    this.numberOfOrders[i] = new NumberOfOrders(body.readLong());
                    this.noOrders[i] = new NoOrders(body.readUnsignedInt());

                    if(this.numberOfOrders[i].getValue() > 0){
                        this.orderQty[i] = new OrderQty[(int) this.noOrders[i].getValue()];
                        for(int j = 0; j < this.noOrders[i].getValue(); j++){
                            this.orderQty[i][j] = new OrderQty(body.readLong());
                        }
                    }
                }
            }
            this.noSubTradingPhaseCodes = new NoSubTradingPhaseCodes(body.readUnsignedInt());
            temp = (int) this.noSubTradingPhaseCodes.getValue();

            if(this.noSubTradingPhaseCodes.getValue() > 0){
                this.subTradingPhaseCode = new SubTradingPhaseCode[temp];
                this.tradingType = new TradingType[temp];
                for(int i = 0; i < this.noSubTradingPhaseCodes.getValue(); i++){
                    this.subTradingPhaseCode[i] = new SubTradingPhaseCode(body.readCharSequence(SubTradingPhaseCode.LENGTH, StandardCharsets.UTF_8).toString());
                    this.tradingType[i] = new TradingType(body.readUnsignedByte());
                }
            }

            this.auctionVolumeTrade = new AuctionVolumeTrade(body.readLong());
            this.auctionValueTrade = new AuctionValueTrade(body.readLong());
        }catch (Exception e){
            System.out.println(e.toString());
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
                System.out.println("买卖盘档位：" + this.mdPriceLevel[i].getValue());
                System.out.println("价位总委托笔数：" + this.numberOfOrders[i].getValue());
                System.out.println("价位揭示委托笔数：" + this.noOrders[i].getValue());

                if(this.noOrders[i].getValue() > 0){
                    for(int j = 0; j < this.noOrders[i].getValue(); j++){
                        System.out.println("委托数量：" + this.orderQty[i][j].getValue());
                    }
                }

            }
        }


        System.out.println("细分交易阶段个数：" + this.noSubTradingPhaseCodes.getValue());
        if(this.noSubTradingPhaseCodes.getValue() > 0){
            for(int i = 0; i <this.noSubTradingPhaseCodes.getValue(); i++){
                System.out.println("交易方式所处的交易阶段代码：" + this.subTradingPhaseCode[i].toString());
                System.out.println("交易方式：" + this.tradingType[i].getValue());
            }
        }


        System.out.println("匹配成交成交量：" + this.auctionVolumeTrade.getValue());
        System.out.println("匹配成交成交金额：" + this.auctionValueTrade.getValue());

        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_300211 (*)");
            if(noMDEntries.getValue() > 0){
                for (int i = 0; i < noMDEntries.getValue(); i++) {

                    if(noOrders[i].getValue() > 0){

                        for (int j = 0; j < noOrders[i].getValue(); j++) {
                            addSameObject(ps);
                            ps.setObject(11, noMDEntries.getValue());
                            ps.setObject(12, mdEntryType[i].getValue());
                            ps.setObject(13, mdEntryPx[i].getValue());
                            ps.setObject(14, mdEntrySize[i].getValue());
                            ps.setObject(15, mdPriceLevel[i].getValue());
                            ps.setObject(16, numberOfOrders[i].getValue());
                            ps.setObject(17, noOrders[i].getValue());
                            ps.setObject(18, orderQty[i][j].getValue());
                            ps.setNull(19, Types.INTEGER);
                            ps.setNull(20, Types.VARCHAR);
                            ps.setNull(21, Types.INTEGER);
                            ps.setNull(22, Types.INTEGER);
                            ps.setNull(23, Types.INTEGER);

                            ps.addBatch();
                        }

                    }
                    else{
                        addSameObject(ps);
                        ps.setObject(11, noMDEntries.getValue());
                        ps.setObject(12, mdEntryType[i].getValue());
                        ps.setObject(13, mdEntryPx[i].getValue());
                        ps.setObject(14, mdEntrySize[i].getValue());
                        ps.setObject(15, mdPriceLevel[i].getValue());
                        ps.setObject(16, numberOfOrders[i].getValue());
                        ps.setObject(17, noOrders[i].getValue());
                        ps.setNull(18, Types.INTEGER);

                        ps.setNull(19, Types.INTEGER);
                        ps.setNull(20, Types.VARCHAR);
                        ps.setNull(21, Types.INTEGER);
                        ps.setNull(22, Types.INTEGER);
                        ps.setNull(23, Types.INTEGER);

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

                ps.setNull(19, Types.INTEGER);
                ps.setNull(20, Types.VARCHAR);
                ps.setNull(21, Types.INTEGER);
                ps.setNull(22, Types.INTEGER);
                ps.setNull(23, Types.INTEGER);

                ps.addBatch();

            }
            ps.executeBatch();
            ps.clearBatch();
            if(noSubTradingPhaseCodes.getValue() > 0){
                for (int i = 0; i < noSubTradingPhaseCodes.getValue(); i++) {
                    addSameObject(ps);
                    ps.setNull(11, Types.INTEGER);
                    ps.setNull(12, Types.VARCHAR);
                    ps.setNull(13, Types.INTEGER);
                    ps.setNull(14, Types.INTEGER);
                    ps.setNull(15, Types.INTEGER);
                    ps.setNull(16, Types.INTEGER);
                    ps.setNull(17, Types.INTEGER);
                    ps.setNull(18, Types.INTEGER);

                    ps.setObject(19, noSubTradingPhaseCodes.getValue());
                    ps.setObject(20, subTradingPhaseCode[i].getValue());
                    ps.setObject(21, tradingType[i].getValue());
                    ps.setObject(22, auctionVolumeTrade.getValue());
                    ps.setObject(23, auctionValueTrade.getValue());
                    ps.addBatch();
                }
            }
            else{
                addSameObject(ps);
                ps.setNull(11, Types.INTEGER);
                ps.setNull(12, Types.VARCHAR);
                ps.setNull(13, Types.INTEGER);
                ps.setNull(14, Types.INTEGER);
                ps.setNull(15, Types.INTEGER);
                ps.setNull(16, Types.INTEGER);
                ps.setNull(17, Types.INTEGER);
                ps.setNull(18, Types.INTEGER);
                
                ps.setObject(19, noSubTradingPhaseCodes.getValue());
                ps.setNull(20, Types.VARCHAR);
                ps.setNull(21, Types.INTEGER);
                ps.setObject(22, auctionVolumeTrade.getValue());
                ps.setObject(23, auctionValueTrade.getValue());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getOwnLength(){
        return NoMDEntries.getLength() + MDEntryType.LENGTH + MDEntryPx.getLength()
                + MDEntrySize.getLength() + MDPriceLevel.getLength() + NumberOfOrders.getLength()
                + NoOrders.getLength() + OrderQty.getLength() + NoSubTradingPhaseCodes.getLength()
                + SubTradingPhaseCode.LENGTH + TradingType.getLength() + AuctionValueTrade.getLength()
                + AuctionVolumeTrade.getLength();
    }
}
