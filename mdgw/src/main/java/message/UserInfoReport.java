package message;

import dataType.OrigTime;
import dataType.Price;
import dataType.UserNum;
import dataType.VersionCode;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class UserInfoReport extends Message{
    //用户信息报告消息
    private OrigTime origTime;
    private VersionCode versionCode;
    private UserNum userNum;

    public UserInfoReport() {
        super(390093);
    }

    public UserInfoReport(OrigTime origTime, VersionCode versionCode, UserNum userNum) {
        super(390093);
        this.origTime = origTime;
        this.versionCode = versionCode;
        this.userNum = userNum;
    }

    @Override
    protected byte[] toBytes() {
        int bodyLength = OrigTime.getLength() + versionCode.getLength() + UserNum.getLength();
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(origTime.toBytes()).put(versionCode.toBytes()).put(userNum.toBytes());
        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.origTime = new OrigTime(body.readLong());
        this.versionCode = new VersionCode(body.readCharSequence(VersionCode.LENGTH, StandardCharsets.UTF_8).toString());
        this.userNum = new UserNum(body.readUnsignedShort());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("数据生成时间：" + origTime.toString());
        System.out.println("版本代码：" + versionCode.toString());
        System.out.println("用户数目:" + userNum.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into mdgw.message_390093 (*)");
            ps.setObject(1, origTime.toDateType());
            ps.setObject(2, versionCode.getValue());
            ps.setObject(3, userNum.getValue());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
