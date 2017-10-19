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
