package netty_message;

import com.fasterxml.jackson.databind.ObjectMapper;
import configs.sysconfig;
import database.connectEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import message.Market_300111;
import message.Message;
import util.BinaryUtil;
import util.Util;

import java.io.FileWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MessageStore extends SimpleChannelInboundHandler<Message> {
    Connection connection;

    public MessageStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        if(this.connection != null){
            message.writeDateBase(connection);
        }

        // 转发给下一条进行处理
        channelHandlerContext.fireChannelRead(message);
    }




}
