package piper.im.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.util.YamlUtil;

/**
 * WebSocket初始化配置
 *
 * @author piper
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public WebSocketServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new LoginHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(config.getWsPath(), null, true, 65536, false, true, 3000L));
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketFrameHandler());
    }
}
