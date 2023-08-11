package message;

import dataType.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Getter
public class Reject extends Message{
    // 业务拒绝消息

    private RefSeqNum refSeqNum;
    private RefMsgType refMsgType;
    private BusinessRejectRefID businessRejectRefID;
    private BusinessRejectReason businessRejectReason;
    private BusinessRejectText businessRejectText;



    public Reject() {
        super(8);
    }

    public Reject(RefSeqNum refSeqNum, RefMsgType refMsgType, BusinessRejectRefID businessRejectRefID, BusinessRejectReason businessRejectReason, BusinessRejectText businessRejectText) {
        super(8);
        this.refSeqNum = refSeqNum;
        this.refMsgType = refMsgType;
        this.businessRejectRefID = businessRejectRefID;
        this.businessRejectReason = businessRejectReason;
        this.businessRejectText = businessRejectText;
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = RefSeqNum.getLength() + RefMsgType.getLength() + businessRejectRefID.getLength()
                + BusinessRejectReason.getLength() + businessRejectText.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(refSeqNum.toBytes())
                .put(refMsgType.toBytes())
                .put(businessRejectRefID.toBytes())
                .put(businessRejectReason.toBytes())
                .put(businessRejectText.toBytes());

        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.refSeqNum = new RefSeqNum(body.readLong());
        this.refMsgType = new RefMsgType(body.readUnsignedInt());
        this.businessRejectRefID = new BusinessRejectRefID(body.readCharSequence(BusinessRejectRefID.LENGTH, StandardCharsets.UTF_8).toString());
        this.businessRejectReason = new BusinessRejectReason(body.readUnsignedShort());
        this.businessRejectText = new BusinessRejectText(body.readCharSequence(BusinessRejectText.LENGTH, StandardCharsets.UTF_8).toString());
    }

    @Override
    protected void toStrings() {

    }

    @Override
    public void writeDateBase(Connection connection) {

    }


}
