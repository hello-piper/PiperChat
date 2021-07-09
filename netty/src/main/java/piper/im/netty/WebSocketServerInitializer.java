package piper.im.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import piper.im.common.pojo.config.ServerProperties;

import java.util.concurrent.TimeUnit;

/**
 * WebSocket初始化配置
 *
 * @author piper
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ServerProperties config;

    public WebSocketServerInitializer(ServerProperties config) {
        this.config = config;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // Configure SSL.
        try {
            if (config.getSsl()) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new LoginHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(config.getWsPath(), null, true, 65536, false, true, 3000L));
        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new IdleStateEventHandler());
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketFrameHandler());
    }
}
