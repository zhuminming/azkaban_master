/**   
* @Title: FlowRunnerManagerTest.java 
* @Package org.azkaban.execserver 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年11月4日 下午3:12:47 
* @version V1.0   
*/
package org.azkaban.execserver;

import java.io.IOException;

import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.project.JdbcProjectLoador;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;
import org.azkaban.common.utils.Utils;
import org.junit.Test;

import azkaban.execapp.FlowRunnerManager;

/** 
 * @ClassName: FlowRunnerManagerTest 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年11月4日 下午3:12:47 
 *  
 */
public class FlowRunnerManagerTest {
    private FlowRunnerManager flowManger;
    
    private void init(){
	
	try {
	    String file = Utils.getRootFilePath();
	    Props props = new Props(null, file);
	    ProjectLoader projectLoader = new JdbcProjectLoador(props);
	    ExecutorLoader executorLoader =new JdbcExecutorLoader(props);
	    flowManger = new FlowRunnerManager(props, executorLoader, projectLoader);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }
    @Test
    public void submitFlow(){
	try {
	    flowManger.submitFlow(1);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
