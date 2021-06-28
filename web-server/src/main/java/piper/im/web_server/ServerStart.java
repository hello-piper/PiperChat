package piper.im.web_server;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.task.WebServerTask;
import piper.im.common.util.YamlUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.servlet;

public class ServerStart {
    public static void main(final String[] args) throws ServletException {
        final PathHandler root = new PathHandler();
        final ServletContainer container = ServletContainer.Factory.newInstance();
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);

        DeploymentInfo build = new DeploymentInfo()
                .setClassLoader(ServerStart.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("web-server.war")
                .addWelcomePages("templates/index.html")
                .setResourceManager(new ClassPathResourceManager(ServerStart.class.getClassLoader(), ServerStart.class.getPackage().getName().replace(".", "/")))
                .addServlets(servlet(ServerServlet.class).addMapping("/server"))
                .addDeploymentCompleteListener(new ServletContextListener() {
                    @Override
                    public void contextInitialized(ServletContextEvent sce) {
                        WebServerTask.start();
                    }
                });

        DeploymentManager manager = container.addDeployment(build);
        manager.deploy();
        root.addPrefixPath(build.getContextPath(), manager.start());

        Undertow server = Undertow.builder().addHttpListener(config.getPort(), "0.0.0.0").setHandler(root).build();
        server.start();
    }
}
