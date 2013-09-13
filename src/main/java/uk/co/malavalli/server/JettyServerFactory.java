package uk.co.malavalli.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PreDestroy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Component
public class JettyServerFactory {

	private static Logger LOG = LoggerFactory.getLogger(JettyServerFactory.class);
	private static final List<Server> sharedServers = Collections.synchronizedList(new ArrayList<Server>());
	private static final List<Server> contextServers = Collections.synchronizedList(new ArrayList<Server>());

	@Autowired
	private ApplicationContext applicationContext;

	public Server createServer(int port) throws Exception {
		return createServer(port, createWebAppContext(applicationContext));
	}

	public Server createServer(int port, WebAppContext webAppContext) throws Exception {
		synchronized (sharedServers) {
			stopServersOnPort(port);
			final Server server = new Server(port);
			registerServer(server);
			configureContext(server, webAppContext);
			return server;
		}
	}

	@PreDestroy
	public void unregisterAllServers() throws Exception {
		synchronized (sharedServers) {
			Iterator<Server> it = contextServers.iterator();
			while (it.hasNext()) {
				Server server = it.next();
				it.remove();
				unregisterServer(server);
			}
		}
	}

	public void unregisterServer(Server server) throws Exception {
		synchronized (sharedServers) {
			LOG.debug("Unregistered server {}", server);
			server.stop();
			server.destroy();
			sharedServers.remove(server);
			contextServers.remove(server);
		}
	}

	private void registerServer(Server server) {
		LOG.debug("Registering server {}", server);
		sharedServers.add(server);
		contextServers.add(server);
	}

	private static void stopServersOnPort(int port) throws Exception {
		for (Server server : sharedServers) {
			if (server.isStarted()) {
				if (server.getConnectors()[0].getPort() == port) {
					LOG.debug("Stopping server {} on port {}", server, port);
					server.stop();
				}
			}
		}
	}

	private void configureContext(Server server, WebAppContext webAppContext) {
		server.setHandler(webAppContext);
	}

	private WebAppContext createWebAppContext(ApplicationContext parent) throws IOException {
		final String absolutePath = new ClassPathResource("webapp").getFile().getAbsolutePath();
		final WebAppContext webAppContext = new WebAppContext(absolutePath, "/");
		final GenericWebApplicationContext genericWebApplicationContext = new GenericWebApplicationContext();
		genericWebApplicationContext.setServletContext(webAppContext.getServletContext());
		if (parent != null) {
			genericWebApplicationContext.setParent(parent);
		}
		genericWebApplicationContext.refresh();
		webAppContext.getServletContext().setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				genericWebApplicationContext);
		return webAppContext;
	}
}
