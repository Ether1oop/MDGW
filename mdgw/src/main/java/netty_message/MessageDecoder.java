package netty_message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import message.Message;
import util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int msgType = byteBuf.getInt(0);
        int bodyLength = byteBuf.getInt(4);
        long checksum = byteBuf.getUnsignedInt(8 + bodyLength);

        byte[] data = new byte[8 + bodyLength + 4];
        byteBuf.readBytes(data);

        if(checksum != Util.GenerateCheckSum(data).getValue()){
            return;
        }

        MsgType.lookup(msgType)
                .flatMap(Util::getMessage)
                .ifPresent(msgClass -> {
                    try {
                        Message ins = msgClass.getDeclaredConstructor().newInstance();
                        Method method = msgClass.getMethod("resumeMessageFrom", byte[].class);
                        method.invoke(ins, data);
                        list.add(ins);
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

    }
}
