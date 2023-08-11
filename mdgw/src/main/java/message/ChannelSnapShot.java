package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import util.BinaryUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

@Getter
public class ChannelSnapShot extends Message{
    //快照行情频道统计
    private OrigTime origTime;
    private ChannelNo channelNo;
    private NoMDStreamID noMDStreamID;
    private MDStreamID[] mdStreamID;
    private StockNum[] stockNum;
    private TradingPhaseCode[] tradingPhaseCode;

    public ChannelSnapShot() {
        super(390090);
    }

    @Override
    protected byte[] toBytes() {
        long bodyLength = OrigTime.getLength() + ChannelNo.getLength() + NoMDStreamID.getLength() + MDStreamID.LENGTH
                + StockNum.getLength() + TradingPhaseCode.LENGTH;
        ByteBuffer buffer = ByteBuffer.allocate((int) bodyLength);
//        buffer.put(BinaryUtil.longToBytes(origTime.getValue()))
//                .put(channelNo.toBytes())
//                .put(noMDStreamID.toBytes())
//                .put(mdStreamID.toBytes())
//                .put(stockNum.toBytes())
//                .put(tradingPhaseCode.toBytes());

        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.noMDStreamID = new NoMDStreamID(body.readUnsignedInt());
        if(this.noMDStreamID.getValue() > 0){
            this.mdStreamID = new MDStreamID[(int) this.noMDStreamID.getValue()];
            this.stockNum = new StockNum[(int) this.noMDStreamID.getValue()];
            this.tradingPhaseCode = new TradingPhaseCode[(int) this.noMDStreamID.getValue()];
            for(int i = 0; i < (int) this.noMDStreamID.getValue(); i++){
                this.mdStreamID[i] = new MDStreamID(body.readCharSequence(MDStreamID.LENGTH, StandardCharsets.UTF_8).toString());
                this.stockNum[i] = new StockNum(body.readUnsignedInt());
                this.tradingPhaseCode[i] = new TradingPhaseCode(body.readCharSequence(TradingPhaseCode.LENGTH, StandardCharsets.UTF_8).toString());
            }
        }

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("频道快照消息");
        System.out.println("数据生成时间：" + this.origTime.toString());
        System.out.println("频道代码：" + this.channelNo.toString());
        System.out.println("行情类别个数:" + this.noMDStreamID.toString());

        if(this.noMDStreamID.getValue() > 0){
            for(int i = 0; i < this.noMDStreamID.getValue(); i++){
                System.out.println("行情类别：" + this.mdStreamID[i].toString());
                System.out.println("证券只数：" + this.stockNum[i].toString());
                System.out.println("闭式状态:" + this.tradingPhaseCode[i].toString());
            }
        }

        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_390090 (*)")) {
            if(noMDStreamID.getValue() > 0){
                for(int i = 0; i < this.noMDStreamID.getValue(); i++){
                    ps.setObject(1, origTime.toDateType());
                    ps.setObject(2, channelNo.getValue());
                    ps.setObject(3, noMDStreamID.getValue());
                    ps.setObject(4, mdStreamID[i].getValue());
                    ps.setObject(5, stockNum[i].getValue());
                    ps.setObject(6, tradingPhaseCode[i].getValue());
                    ps.addBatch();
                }
            }
            else{
                ps.setObject(1, origTime.toDateType());
                ps.setObject(2, channelNo.getValue());
                ps.setObject(3, noMDStreamID.getValue());
                ps.setObject(4, Types.NULL);
                ps.setObject(5, Types.NULL);
                ps.setObject(6, Types.NULL);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
