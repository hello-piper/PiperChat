package piper.im.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(LoginHandler.class);

    public static final AttributeKey<String> TOKEN_ATTRIBUTE_KEY = AttributeKey.valueOf("token");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri = ((FullHttpRequest) msg).uri();
        if (!"/".equals(uri)) {
            String[] split = uri.split("\\?");
            if (split.length < 2) {
                throw new RuntimeException("请登录");
            }
            String url = split[0];
            String param = split[1];
            log.info("uri:{}, url:{}, param:{}", uri, url, param);
            Map<String, String> paramMap = new HashMap<>();
            String[] params = param.split("&");
            for (String s : params) {
                String[] p = s.split("=");
                paramMap.put(p[0], p[1]);
            }
            String token = paramMap.get(TOKEN_ATTRIBUTE_KEY.name());
            log.info("token:{}", token);
            ctx.channel().attr(TOKEN_ATTRIBUTE_KEY).set(token);
            if (Objects.isNull(token)) {
                throw new RuntimeException("请登录");
            }
        }
        ctx.pipeline().remove(LoginHandler.class);
        super.channelRead(ctx, msg);
    }
}
