package netty_message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import message.Heartbeat;

import java.util.concurrent.TimeUnit;

public class ReadIdleStateHandler extends IdleStateHandler {

    private Heartbeat heartbeat = new Heartbeat();

    public ReadIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        ctx.channel().writeAndFlush(heartbeat);
        System.out.println("send heartbeat...");
        super.channelIdle(ctx, evt);
    }
}
