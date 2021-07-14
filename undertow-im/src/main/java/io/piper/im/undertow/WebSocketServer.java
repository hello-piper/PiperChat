package io.piper.im.undertow;

import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.task.ImServerTask;
import io.piper.common.util.YamlUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

/**
 * WebSocketServer
 *
 * @author piper
 */
public class WebSocketServer {
    public static void main(final String[] args) throws ServletException {
        final PathHandler root = new PathHandler();
        final ServletContainer container = ServletContainer.Factory.newInstance();
        final ServerProperties config = YamlUtil.getConfig("server", ServerProperties.class);

        DeploymentInfo builder = new DeploymentInfo()
                .setClassLoader(WebSocketServer.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("undertow.war")
                .addWelcomePage("templates/index.html")
                .setResourceManager(new ClassPathResourceManager(WebSocketServer.class.getClassLoader()))
                .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                        new WebSocketDeploymentInfo().addEndpoint(WebSocketEndpoint.class)
                                .setBuffers(new DefaultByteBufferPool(true, 100))
                )
                .addDeploymentCompleteListener(new ServletContextListener() {
                    @Override
                    public void contextInitialized(ServletContextEvent sce) {
                        ImServerTask.start();
                        System.out.println("Open your web browser and navigate to " +
                                (config.getSsl() ? "https" : "http") + "://127.0.0.1:" + config.getPort() + '/');
                    }
                });

        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
        root.addPrefixPath(builder.getContextPath(), manager.start());

        Undertow server = Undertow.builder().addHttpListener(config.getPort(), "0.0.0.0").setHandler(root).build();
        server.start();
    }
}
