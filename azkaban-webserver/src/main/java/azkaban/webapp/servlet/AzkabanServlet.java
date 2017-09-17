package azkaban.webapp.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class AzkabanServlet extends AbstractAzkabanServlet {

	private static final long serialVersionUID = 2L;
	private final Logger logger = Logger.getLogger(AzkabanServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		VelocityEngine engine = new VelocityEngine();
		VelocityContext context = new VelocityContext();
		context.put("context", request.getContextPath());
		context.put("azkaban_name", "Local");
		context.put("azkaban_label", "My Local Azkaban");
		Properties prop = new Properties();                   // 设置vm模板的装载路径
		String path = System.getProperty("user.dir");

		prop.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path + "/src/main/java/");
		engine.init(prop);
         
		response.setHeader("Content-type", "text/html; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html");

	    boolean status = engine.mergeTemplate("azkaban/webapp/servlet/velocity/index.vm", "UTF-8", context, response.getWriter());
	
	    if(status){
	    	logger.info("merge Template success!");
	    }
	}

}
