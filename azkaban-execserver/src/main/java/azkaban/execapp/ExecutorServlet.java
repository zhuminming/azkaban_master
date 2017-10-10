package azkaban.execapp;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.azkaban.common.executor.ConnectorParams;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class ExecutorServlet extends HttpServlet implements ConnectorParams {

    /**
     * 
     */
    private static final long serialVersionUID = -8247820901136364263L;
    private static final Logger logger = Logger.getLogger(ExecutorServlet.class);
    private AzkabanExecutorServer application;
    private ExecutorManager execmanager;
    private static final String AZKABAN_SERVLET_CONTEXT_KEY = "azkaban_app";
    private static final String JSON_MIME_TYPE = "application/json";
    public ExecutorServlet() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	application = (AzkabanExecutorServer) config.getServletContext()
		.getAttribute(AZKABAN_SERVLET_CONTEXT_KEY);
	if (application == null) {
	    throw new IllegalStateException(
		    "No batch application is defined in the servlet context!");
	}
	
	execmanager=application.getExecmanager();
    }

    private void writeJson(HttpServletResponse resp, Object obj)
	    throws IOException {
	resp.setContentType(JSON_MIME_TYPE);
	resp.getWriter().append(JSONObject.toJSONString(obj));
    }

    private boolean hasParam(HttpServletRequest request, String param) {
	return request.getParameter(param) != null;
    }

    private String getParam(HttpServletRequest request, String param)
	    throws ServletException {
	String p = (String) request.getParameter(param);
	if (p == null)
	    throw new ServletException("Missing required parameter '" + param + "'.");
	else
	    return p;

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	Map<String, Object> respMap = Maps.newHashMap();
	if(hasParam(req, ACTION_PARAM)){
	    String action = getParam(req, ACTION_PARAM);
	    int execid = Integer.parseInt(getParam(req, EXECID_PARAM));
	    if(action.equals(EXECUTE_ACTION)){
		handleAjaxExecute(req, respMap, execid);
	    }
	}
	writeJson(resp,respMap);
    }
    
    
    private void handleAjaxExecute(HttpServletRequest req ,Map<String, Object> respMap,int execid){
	try {
	    execmanager.submitFlow(execid);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
