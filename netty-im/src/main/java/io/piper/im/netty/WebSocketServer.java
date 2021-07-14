package io.piper.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.task.ImServerTask;
import io.piper.common.util.YamlUtil;

/**
 * WebSocketServer
 *
 * @author piper
 */
public final class WebSocketServer {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketServer.class);

    public static void main(String[] args) throws Exception {
        final ServerProperties config = YamlUtil.getConfig("server", ServerProperties.class);
        EventLoopGroup bossGroup = new NioEventLoopGroup(8);
        EventLoopGroup workerGroup = new NioEventLoopGroup(100);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new WebSocketServerInitializer(config));
            Channel channel = bootstrap.bind(config.getPort()).sync().channel();
            ImServerTask.start();
            log.info("Open your web browser and navigate to " + (config.getSsl() ? "https" : "http") + "://127.0.0.1:" + config.getPort());
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
