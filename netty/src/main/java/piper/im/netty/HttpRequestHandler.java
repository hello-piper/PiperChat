package piper.im.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import piper.im.common.pojo.message.Message;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.util.YamlUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * Outputs index page content.
 *
 * @author piper
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // Handle a bad request.
        if (!req.decoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), BAD_REQUEST, ctx.alloc().buffer(0)));
            return;
        }

        // Allow only GET methods.
        if (!GET.equals(req.method()) && !POST.equals(req.method())) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), FORBIDDEN, ctx.alloc().buffer(0)));
            return;
        }

        // Send the index page
        if ("/".equals(req.uri()) || "/index.html".equals(req.uri())) {
            ByteBuf content = getIndexContent();
            FullHttpResponse res = new DefaultFullHttpResponse(req.protocolVersion(), OK, content);

            res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            HttpUtil.setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
        } else if (config.getHttpPath().equals(req.uri())) {
            int len = req.content().readableBytes();
            if (len > 0) {
                byte[] content = new byte[len];
                req.content().readBytes(content);
                String contentStr = new String(content, StandardCharsets.UTF_8);
                MessageHandler.send(JSONObject.parseObject(contentStr, Message.class));
            }
            FullHttpResponse res = new DefaultFullHttpResponse(req.protocolVersion(), OK, Unpooled.directBuffer(0));
            sendHttpResponse(ctx, req, res);
        } else {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), NOT_FOUND, ctx.alloc().buffer(0)));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        HttpResponseStatus responseStatus = res.status();
        if (responseStatus.code() != 200) {
            ByteBufUtil.writeUtf8(res.content(), responseStatus.toString());
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }
        // Send the response and close the connection if necessary.
        boolean keepAlive = HttpUtil.isKeepAlive(req) && responseStatus.code() == 200;
        HttpUtil.setKeepAlive(res, keepAlive);
        ChannelFuture future = ctx.writeAndFlush(res);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
    }

    private ByteBuf getIndexContent() throws Exception {
        InputStream stream = this.getClass().getResourceAsStream("/index.html");

        int length;
        byte[] buffer = new byte[1024];
        ByteBuf byteBuf = Unpooled.directBuffer();
        while ((length = stream.read(buffer)) > 0) {
            byteBuf.writeBytes(buffer, 0, length);
        }

        return byteBuf;
    }

}
