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
public class SecurityLiveStatus extends Message{
    // 证券实时状态
    private OrigTime origTime;
    private ChannelNo channelNo;
    private SecurityID securityID;
    private SecurityIDSource securityIDSource;
    private FinancialStatus financialStatus;
    private NoSwitch noSwitch;
    private SecuritySwitchType[] securitySwitchType;
    private SecuritySwitchStatus[] securitySwitchStatus;


    public SecurityLiveStatus() {
        super(390013);
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = OrigTime.getLength() + ChannelNo.getLength() + SecurityID.LENGTH
                + SecurityIDSource.LENGTH + FinancialStatus.LENGTH + NoSwitch.getLength()
                + SecuritySwitchType.getLength() + SecuritySwitchStatus.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
//        buffer.put(origTime.toBytes())
//                .put(channelNo.toBytes())
//                .put(securityID.toBytes())
//                .put(securityIDSource.toBytes())
//                .put(financialStatus.toBytes())
//                .put(noSwitch.toBytes())
//                .put(securitySwitchType.toBytes())
//                .put(securitySwitchStatus.toBytes());

        return buffer.array();

    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.channelNo = new ChannelNo(body.readUnsignedShort());
        this.securityID = new SecurityID(body.readCharSequence(SecurityID.LENGTH, StandardCharsets.UTF_8).toString());
        this.securityIDSource = new SecurityIDSource(body.readCharSequence(SecurityIDSource.LENGTH, StandardCharsets.UTF_8).toString());
        this.financialStatus = new FinancialStatus(body.readCharSequence(FinancialStatus.LENGTH, StandardCharsets.UTF_8).toString());
        this.noSwitch = new NoSwitch(body.readUnsignedInt());

        if(this.noSwitch.getValue() > 0){
            this.securitySwitchType = new SecuritySwitchType[(int) this.noSwitch.getValue()];
            this.securitySwitchStatus = new SecuritySwitchStatus[(int) this.noSwitch.getValue()];

            for(int i = 0; i < this.noSwitch.getValue(); i++){
                this.securitySwitchType[i] = new SecuritySwitchType(body.readUnsignedShort());
                this.securitySwitchStatus[i] = new SecuritySwitchStatus(body.readUnsignedShort());
            }
        }


    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("数据生成时间:" + this.origTime.getValue());
        System.out.println("频道代码:" + this.channelNo.getValue());
        System.out.println("证券代码:" + this.securityID.toString());
        System.out.println("证券代码源:" + this.securityIDSource.toString());
        System.out.println("证券状态:" + this.financialStatus.getLength());
        System.out.println("开关个数:" + this.noSwitch.getValue());

        if(this.noSwitch.getValue() > 0){
            for(int i = 0; i < this.noSwitch.getValue(); i++){
                System.out.println("开关类别:" + this.securitySwitchType[i].getValue());
                System.out.println("开关状态:" + this.securitySwitchStatus[i].getValue());
            }
        }

        System.out.println("----------------------------------------");

    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_390013 (*)");


            if(noSwitch.getValue() > 0){
                for (int i = 0; i < noSwitch.getValue(); i++) {
                    ps.setObject(1, origTime.toDateType());
                    ps.setObject(2, channelNo.getValue());
                    ps.setObject(3, securityID.getValue());
                    ps.setObject(4, securityIDSource.getValue());
                    ps.setObject(5, financialStatus.getValue());
                    ps.setObject(6, noSwitch.getValue());
                    ps.setObject(7, securitySwitchType[i].getValue());
                    ps.setObject(8, securitySwitchStatus[i].getValue());
                    ps.addBatch();
                }
            }
            else {
                ps.setObject(1, origTime.toDateType());
                ps.setObject(2, channelNo.getValue());
                ps.setObject(3, securityID.getValue());
                ps.setObject(4, securityIDSource.getValue());
                ps.setObject(5, financialStatus.getValue());
                ps.setObject(6, noSwitch.getValue());
                ps.setObject(7, Types.NULL);
                ps.setObject(8, Types.NULL);
                ps.addBatch();

            }
            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
