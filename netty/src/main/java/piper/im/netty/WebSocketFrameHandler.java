package piper.im.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import piper.im.common.pojo.message.Message;

/**
 * Echoes uppercase content of text frames.
 *
 * @author piper
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketFrameHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.debug("用户：{} 上线", ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.debug("用户: {} 下线", ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        if (frame != null) {
            // Send the uppercase string back.
            MessageHandler.send(JSON.parseObject(frame.text(), Message.class));
        } else {
            throw new UnsupportedOperationException("frame is null");
        }
    }

}
