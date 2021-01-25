package piper.im.web_server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.*;

/**
 * @author Stuart Douglas
 */
public class ServerStart {

    public static final String MYAPP = "/";

    public static void main(final String[] args) {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(ServerStart.class.getClassLoader())
                    .setContextPath(MYAPP)
                    .setDeploymentName("test.war")
                    .addServlets(
                            servlet("AddressServlet", AddressServlet.class)
                                    .addInitParam("message", "Hello World")
                                    .addMapping("/address"),
                            servlet("ServerServlet", ServerServlet.class)
                                    .addInitParam("message", "Hello World")
                                    .addMapping("/server"));

            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
                    .addPrefixPath(MYAPP, servletHandler);
            Undertow server = Undertow.builder()
                    .addHttpListener(8080, "localhost")
                    .setHandler(path)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
