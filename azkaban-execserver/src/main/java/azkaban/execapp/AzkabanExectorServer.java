package azkaban.execapp;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class AzkabanExectorServer {
	private Server server;
	
	public AzkabanExectorServer(){
		this.server=new Server();
	}
	public static void main(String[] args) throws Exception{
		Server server = new Server(8081);
		Context root = new Context(server, "/",Context.SESSIONS);
		root.addServlet(new ServletHolder(new ExectorServlet()), "/executor");
		server.start();
	}

}
