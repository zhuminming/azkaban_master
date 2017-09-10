package org.azkaban.webserver;

import org.apache.velocity.app.VelocityEngine;
import org.azkaban.common.server.AzkabanServer;
import org.azkaban.common.utils.Props;
import org.azkaban.webserver.servlet.AzkabanServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;


/**
 * @author zmm
 *
 */
public class AzkabanWebServer extends AzkabanServer 
{
	private Props props;
	private Server server;
	public AzkabanWebServer(Props props){
		this.props = props;
		server =new Server(8080);
		Context root = new Context(server, ".", Context.SESSIONS);
		root.addServlet(new ServletHolder(new AzkabanServlet()), "/index");
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		
		
	}
	/* (non-Javadoc)
	 * @see org.azkaban.common.server.AzkabanServer#getServerProps()
	 */
	@Override
	public Props getServerProps() {
		// TODO Auto-generated method stub
		return this.props;
	}
	/* (non-Javadoc)
	 * @see org.azkaban.common.server.AzkabanServer#getVelocityEngine()
	 */
	@Override
	public VelocityEngine getVelocityEngine() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
