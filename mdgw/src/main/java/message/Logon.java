package message;

import dataType.CompID;
import dataType.DefaultApplVerID;
import dataType.Password;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Getter
public class Logon extends Message{

    // 发送方代码
    private CompID SenderCompID;
    // 接收方代码
    private CompID TargetCompID;
    // 心跳监测的时间间隔 为系统设定值,以秒为单位
    private int HeartBtInt;
    // 密码
    private Password password;
    // 通信版本号。填写为 n.xy。如通信版本号为 1.01 时，则字段应设置为 1.01。
    private DefaultApplVerID defaultApplVerID;


    public Logon() {
        super(1);
    }

    public Logon(CompID senderCompID, CompID targetCompID, int heartBtInt, Password password, DefaultApplVerID defaultApplVerID) {
        super(1);
        SenderCompID = senderCompID;
        TargetCompID = targetCompID;
        HeartBtInt = heartBtInt;
        this.password = password;
        this.defaultApplVerID = defaultApplVerID;
    }

    public Logon(String senderCompID, String targetCompID, int heartBtInt, String password, String defaultApplVerID){
        super(1);
        SenderCompID = new CompID(senderCompID);
        TargetCompID = new CompID(targetCompID);
        HeartBtInt = heartBtInt;
        this.password = new Password(password);
        this.defaultApplVerID = new DefaultApplVerID(defaultApplVerID);
    }

    @Override
    public byte[] toBytes(){
        byte[] sender = this.SenderCompID.toBytes();
        byte[] target = this.TargetCompID.toBytes();
        byte[] password = this.password.toBytes();
        byte[] defaultApplVerId = this.defaultApplVerID.toBytes();
        int bodyLength = sender.length + target.length + password.length + defaultApplVerId.length + 4;
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength);
        buffer.put(sender)
                .put(target)
                .putInt(HeartBtInt)
                .put(password)
                .put(defaultApplVerId);
        return buffer.array();
    }

    @Override
    public void read(ByteBuf body){
        // 发送方编号
        this.SenderCompID = new CompID(body.readCharSequence(CompID.LENGTH, StandardCharsets.UTF_8).toString());
        // 接收方编号
        this.TargetCompID = new CompID(body.readCharSequence(CompID.LENGTH, StandardCharsets.UTF_8).toString());
        // 心跳间隔
        this.HeartBtInt = body.readInt();
        // 密码
        this.password = new Password(body.readCharSequence(Password.LENGTH, StandardCharsets.UTF_8).toString());
        // 版本号
        this.defaultApplVerID = new DefaultApplVerID(body.readCharSequence(DefaultApplVerID.LENGTH, StandardCharsets.UTF_8).toString());
    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("消息类型：登录消息");
        System.out.println("发送方代码：" + this.SenderCompID.toString());
        System.out.println("接收方代码：" + this.TargetCompID.toString());
        System.out.println("心跳间隔时间：" + this.HeartBtInt);
        System.out.println("密码：" + this.password.toString());
        System.out.println("通信版本号:" + defaultApplVerID.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {

    }


    public int getHeartBtInt() {
        return HeartBtInt;
    }
}
