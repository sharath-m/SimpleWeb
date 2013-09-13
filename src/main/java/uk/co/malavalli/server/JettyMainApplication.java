package uk.co.malavalli.server;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * This is a application that runs jetty with a spring context
 * 
 */
public class JettyMainApplication {
	private final static String[] DEFAULT_APPLICATION_CONTEXTS = new String[]
	{ "application-context.xml" };

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);
		configureContext(server, args.length == 0 ? DEFAULT_APPLICATION_CONTEXTS : args);
		server.setStopAtShutdown(true);
		server.start();

		System.out.printf("Running on\nhttp://localhost:%d/", server.getConnectors()[0].getPort());
	}

	protected static void configureContext(Server server, String[] appContexts) throws Exception
	{
		ApplicationContext applicationContext = createApplicationContext(appContexts);
		final String absolutePath = new
				ClassPathResource("webapp").getFile().getAbsolutePath();
		final WebAppContext webAppContext = new WebAppContext(absolutePath, "/");
		// final WebAppContext webAppContext = new WebAppContext();
		// webAppContext.setDescriptor(webAppContext + "/WEB-INF/web.xml");
		// webAppContext.setResourceBase(".");
		// webAppContext.setContextPath("/");
		final GenericWebApplicationContext webApplicationContext = new GenericWebApplicationContext();
		webApplicationContext.setServletContext(webAppContext.getServletContext());
		webApplicationContext.setParent(applicationContext);
		webApplicationContext.refresh();
		webAppContext.getServletContext().setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				webApplicationContext);
		server.setHandler(webAppContext);
	}

	protected static AbstractXmlApplicationContext createApplicationContext(String[] appContexts) {
		ClassLoader classLoader = JettyMainApplication.class.getClassLoader();
		List<String> contexts = new ArrayList<String>(appContexts.length);
		for (String context : appContexts) {
			if (classLoader.getResourceAsStream(context) != null) {
				contexts.add(context);
			} else {
				System.err.println("Could not find application context file " + context);
			}
		}
		return new ClassPathXmlApplicationContext(contexts.toArray(new String[contexts.size()]));
	}

}
