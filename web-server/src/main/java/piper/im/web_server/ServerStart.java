package piper.im.web_server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import piper.im.common.task.ServerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.*;

/**
 * @author Stuart Douglas
 */
public class ServerStart {

    public static void main(final String[] args) {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServerStart.class.getClassLoader())
                    .setContextPath("/")
                    .setDeploymentName("web-server.war")
                    .addWelcomePage("templates/index.html")
                    .setResourceManager(new ClassPathResourceManager(ServerStart.class.getClassLoader(), ""))
                    .addServlets(servlet(ServerServlet.class).addMapping("/server"))
                    .addDeploymentCompleteListener(new ServletContextListener() {
                        @Override
                        public void contextInitialized(ServletContextEvent sce) {
                            ServerTask.start();
                        }
                    });

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            PathHandler path = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", manager.start());

            Undertow server = Undertow.builder()
                    .addHttpListener(8090, "0.0.0.0")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
