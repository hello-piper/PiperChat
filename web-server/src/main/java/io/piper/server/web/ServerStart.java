/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.server.web;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.task.WebServerTask;
import io.piper.common.util.YamlUtil;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.filter;
import static io.undertow.servlet.Servlets.servlet;

/**
 * ServerStart
 *
 * @author piper
 */
public class ServerStart {
    public static void main(final String[] args) throws ServletException {
        final PathHandler root = new PathHandler();
        final ServletContainer container = ServletContainer.Factory.newInstance();
        ServerProperties config = YamlUtil.getConfig("server", ServerProperties.class);

        DeploymentInfo build = new DeploymentInfo()
                .setClassLoader(ServerStart.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("web-server.war")
                .addWelcomePages("templates/index.html")
                .setResourceManager(new ClassPathResourceManager(ServerStart.class.getClassLoader(), ServerStart.class.getPackage().getName().replace(".", "/")))
                .addServlets(servlet(ChatServlet.class).addMapping("/chat"), servlet(LoginServlet.class).addMapping("/login"))
                .addFilter(filter(ErrorFilter.class)).addFilterUrlMapping("ErrorFilter", "/*", DispatcherType.REQUEST)
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
