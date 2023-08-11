package message;

import dataType.Text;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Getter
public class Logout extends Message{
    /*  退出时的会话状态。
        0 =  会话活跃
        1 =  会话口令已更改
        2 =  将过期的会话口令
        3 =  新会话口令不符合规范
        4 = 会话退登完成
        5 =  不合法的用户名或口令
        6 =  账户锁定
        7 =  当前时间不允许登录
        8 = 口令过期
        9 =  收到的  MsgSeqNum(34)太小
        10 =  收到的  NextExpectedMsgSeqNum(789)太大.
        101 =  其他
        102 =  无效消息
    */
    // 会话状态
    private int SessionStatus;
    // 文本信息.可能包含中文字符，表示最多 200 个字节
    private Text text;

    public Logout() {
        super(2);
    }

    public Logout(int sessionStatus, Text text) {
        super(2);
        SessionStatus = sessionStatus;
        this.text = text;
    }

    @Override
    protected byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4 + text.getLength());
        buffer.putInt(this.SessionStatus).put(text.toBytes());

        return buffer.array();
    }

    @Override
    protected void read(ByteBuf body) {
        this.SessionStatus = body.readInt();
        this.text = new Text(body.readCharSequence(text.getLength(), StandardCharsets.UTF_8).toString());

    }

    @Override
    protected void toStrings() {
        System.out.println("----------------------------------------");
        System.out.println("消息类型：注销消息");
        System.out.println("会话状态：" + this.SessionStatus);
        System.out.println("文本信息：" + this.text.toString());
        System.out.println("----------------------------------------");
    }

    @Override
    public void writeDateBase(Connection connection) {

    }


}
