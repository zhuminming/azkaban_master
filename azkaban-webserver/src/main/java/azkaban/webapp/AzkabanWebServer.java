package azkaban.webapp;

import org.apache.commons.chain.web.WebContext;
import org.azkaban.common.utils.Props;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import azkaban.webapp.servlet.AzkabanServlet;

/**
 * @author zmm
 *
 */
public class AzkabanWebServer {
	private Server server;

	public AzkabanWebServer() {
		String staticDir ="web/";
		server = new Server(8080);
//		WebAppContext root = new WebAppContext();
		ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
		root.setContextPath("/");
		root.setResourceBase(staticDir);
		root.addServlet(new ServletHolder(new AzkabanServlet()), "/index");
		server.setHandler(root);
	}

	public void start(){
		try {
			server.start();
			server.join();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// Props props = AzkabanServer.loadProps(args);
		AzkabanWebServer app = new AzkabanWebServer();
		app.start();
	}
}
