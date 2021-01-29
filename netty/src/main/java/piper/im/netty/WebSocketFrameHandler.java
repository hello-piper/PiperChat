package piper.im.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import piper.im.common.pojo.MessageDTO;

/**
 * Echoes uppercase content of text frames.
 *
 * @author piper
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketFrameHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        logger.debug("用户：" + ctx.channel().id().asLongText() + "上线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        logger.debug("用户下线: " + ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        if (frame != null) {
            // Send the uppercase string back.
            MessageHandler.send(JSON.parseObject(frame.text(), MessageDTO.class));
        } else {
            throw new UnsupportedOperationException("frame is null");
        }
    }

}
