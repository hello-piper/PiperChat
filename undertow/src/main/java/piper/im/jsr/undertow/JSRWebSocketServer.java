package piper.im.jsr.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.task.GatewayTask;
import piper.im.common.util.YamlUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

public class JSRWebSocketServer {

    public static void main(final String[] args) {
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);

        PathHandler path = Handlers.path();

        Undertow server = Undertow.builder()
                .addHttpListener(config.getPort(), "0.0.0.0")
                .setHandler(path)
                .build();
        server.start();

        final ServletContainer container = ServletContainer.Factory.newInstance();

        DeploymentInfo builder = new DeploymentInfo()
                .setClassLoader(JSRWebSocketServer.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("undertow.war")
                .addWelcomePage("templates/index.html")
                .setResourceManager(new ClassPathResourceManager(JSRWebSocketServer.class.getClassLoader(), ""))
                .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                        new WebSocketDeploymentInfo().addEndpoint(JsrChatWebSocketEndpoint.class)
                                .setBuffers(new DefaultByteBufferPool(true, 100))
                )
                .addServlet(Servlets.servlet(MessageServlet.class).addMapping(config.getHttpPath()))
                .addDeploymentCompleteListener(new ServletContextListener() {
                    @Override
                    public void contextInitialized(ServletContextEvent sce) {
                        GatewayTask.start();
                        System.out.println("Open your web browser and navigate to " +
                                (config.getSsl() ? "https" : "http") + "://127.0.0.1:" + config.getPort() + '/');
                    }
                });

        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
        try {
            path.addPrefixPath("/", manager.start());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

}
