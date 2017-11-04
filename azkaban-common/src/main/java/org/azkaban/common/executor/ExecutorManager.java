/**   
 * @Title: ExecutorManager.java 
 * @Package org.azkaban.common.executor 
 * @Description: TODO 
 * @author minming.zhu  
 * @date 2017年10月10日 下午9:48:11 
 * @version V1.0   
 */
package org.azkaban.common.executor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.azkaban.common.flow.Flow;
import org.azkaban.common.project.Project;
import org.azkaban.common.utils.Props;

/**
 * @ClassName: ExecutorManager
 * @Description: TODO
 * @author minming.zhu
 * @date 2017年10月10日 下午9:48:11
 * 
 */
public class ExecutorManager {
    private ExecutorLoader executorLoader;
    public ExecutorManager(Props props){
	executorLoader = new JdbcExecutorLoader(props);
    }

    public String submitExecutableFlow(ExecutableFlow exflow) {
	try {
	    executorLoader.uploadExecutableFlow(exflow);
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

    private void callExecutorServer(String host, int port,
	      String action, Integer executionId){
	String uri ="http://"+host+":"+port+"/executor";
	HttpClient httpclient =  new DefaultHttpClient();
	HttpGet httpget = new HttpGet(uri);
	ResponseHandler<String> responseHandler = new BasicResponseHandler();
	String response = null;
	try {
	    response = httpclient.execute(httpget, responseHandler);
	} catch (ClientProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    
    public static void main(String[] args){
	Flow flow =new Flow("flow1");
	Project project = new Project(1, "project1");
	ExecutableFlow exflow = new ExecutableFlow(project,flow);
	String path = "E:/workspace/azkaban-master/azkaban/azkaban-execserver/src/main/resources/azkaban.properties";
	File file = new File(path);
	Props props = new Props(null,file);
	ExecutorManager executorManager = new ExecutorManager(props);
	executorManager.submitExecutableFlow(exflow);
    }
}
