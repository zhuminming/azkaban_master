package org.azkaban.webserver.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AzkabanServlet extends AbstractAzkabanServlet {

	private static final long serialVersionUID = 2L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		Page page =
		        newPage(req, resp, "azkaban/webapp/servlet/velocity/index.vm");
		page.render();
	}

}
