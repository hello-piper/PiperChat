package piper.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.task.ImServerTask;
import piper.im.common.util.YamlUtil;

import java.util.concurrent.TimeUnit;

/**
 * WebSocketServer
 *
 * @author piper
 */
public final class WebSocketServer {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketServer.class);
    private static ServerBootstrap bootstrap;
    private static ChannelFuture channelFuture;

    public static void main(String[] args) throws Exception {
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);

        // Configure SSL.
        final SslContext sslCtx;
        if (config.getSsl()) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        try {
            bootstrap = newServerBootstrap();
            bootstrap.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketServerInitializer(sslCtx));
            ChannelFuture channelFuture = bootstrap.bind(config.getPort()).sync();

            ImServerTask.start();
            log.info("Open your web browser and navigate to " + (config.getSsl() ? "https" : "http") + "://127.0.0.1:{}", config.getPort());

            channelFuture.channel().closeFuture().sync();
        } finally {
            close();
        }
    }

    public static ServerBootstrap newServerBootstrap() {
        if (Epoll.isAvailable()) {
            EventLoopGroup bossGroup = new EpollEventLoopGroup(1, new DefaultThreadFactory("WebSocketBossGroup", true));
            EventLoopGroup workerGroup = new EpollEventLoopGroup(new DefaultThreadFactory("WebSocketWorkerGroup", true));
            return new ServerBootstrap().group(bossGroup, workerGroup).channel(EpollServerSocketChannel.class);
        } else {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            return new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        }
    }

    public static void close() {
        if (bootstrap == null) {
            log.info("WebSocket server is not running!");
            return;
        }
        log.info("WebSocket server is stopping");
        if (channelFuture != null) {
            channelFuture.channel().close().awaitUninterruptibly(10, TimeUnit.SECONDS);
            channelFuture = null;
        }
        if (bootstrap != null && bootstrap.config().group() != null) {
            bootstrap.config().group().shutdownGracefully();
        }
        if (bootstrap != null && bootstrap.config().childGroup() != null) {
            bootstrap.config().childGroup().shutdownGracefully();
        }
        bootstrap = null;
        log.info("WebSocket server stopped");
    }

}
