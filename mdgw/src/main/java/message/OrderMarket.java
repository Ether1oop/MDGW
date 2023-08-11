package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
public abstract class OrderMarket extends Message{

    private ChannelNo channelNo;
    private ApplSeqNum applSeqNum;
    private MDStreamID mdStreamID;
    private SecurityID securityID;
    private SecurityIDSource securityIDSource;
    private Price price;
    private OrderQty orderQty;
    private Side side;
    private TransactTime transactTime;

    public OrderMarket(long msgType) {
        super(msgType);
    }

    protected void addSameStatement(PreparedStatement ps) throws SQLException {
        ps.setObject(1, channelNo.getValue());
        ps.setObject(2, applSeqNum.getValue());
        ps.setObject(3, mdStreamID.getValue());
        ps.setObject(4, securityID.getValue());
        ps.setObject(5, securityIDSource.getValue());
        ps.setObject(6, price.getValue());
        ps.setObject(7, orderQty.getValue());
        ps.setObject(8, side.getValue());
        ps.setObject(9, transactTime.toDateType());
    }

    @Override
    protected byte[] toBytes() {
        return new byte[0];
    }

    @Override
    protected void read(ByteBuf body) {
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.applSeqNum = new ApplSeqNum(body.readLong());
        this.mdStreamID = new MDStreamID(body.readCharSequence(MDStreamID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityID = new SecurityID(body.readCharSequence(SecurityID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityIDSource = new SecurityIDSource(body.readCharSequence(SecurityIDSource.LENGTH, StandardCharsets.UTF_8).toString());
        this.price = new Price(body.readLong());
        this.orderQty = new OrderQty(body.readLong());
        this.side = new Side(body.readCharSequence(Side.LENGTH, StandardCharsets.UTF_8).toString());
        this.transactTime = new TransactTime(body.readLong());
    }

    @Override
    protected void toStrings() {
        System.out.println("频道代码:" + this.channelNo.getValue());
        System.out.println("消息记录号:" + this.applSeqNum.getValue());
        System.out.println("行情类别:" + this.mdStreamID.toString());
        System.out.println("证券代码:" + this.securityID.toString());
        System.out.println("证券代码源:" + this.securityIDSource.toString());
        System.out.println("委托价格:" + this.price.getValue());
        System.out.println("委托数量:" + this.orderQty.getValue());
        System.out.println("买卖方向:" + this.side.toString());
        System.out.println("委托时间:" + this.transactTime.getValue());

    }



    public int getOwnLength(){
        return ChannelNo.getLength() + ApplEndSeqNum.getLength() + MDStreamID.LENGTH + SecurityID.LENGTH + SecurityIDSource.LENGTH
                + Price.getLength() + OrderQty.getLength() + Side.LENGTH + TransactTime.getLength();
    }

}
