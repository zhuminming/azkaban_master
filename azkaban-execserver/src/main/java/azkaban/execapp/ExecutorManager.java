/**   
* @Title: ExecutorManager.java 
* @Package azkaban.execapp 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月21日 下午10:31:05 
* @version V1.0   
*/
package azkaban.execapp;

import java.util.Map;

import org.azkaban.common.executor.ExecutableFlow;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;

import com.google.common.collect.Maps;

/** 
 * @ClassName: ExecutorManager 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年9月21日 下午10:31:05 
 *  
 */
public class ExecutorManager {
    private Props props;
    private ExecutorLoader executorLoader;
    private ProjectLoader projectLoader;
    private Map<Integer, FlowRunner> runningFlows =Maps.newHashMap();
    
    public ExecutorManager(Props props ,ExecutorLoader executorLoader,ProjectLoader projectLoader){
	this.props = props;
	this.executorLoader = executorLoader;
	this.projectLoader = projectLoader;
    }


    /**
    * @Title: submitFlow 
    * @Description: TODO 
    * @param @param execid  
    * @return void
    * @throws Exception
    */
    public void submitFlow(int execid) throws Exception {
	// TODO Auto-generated method stub
	if(runningFlows.containsKey(execid)){
	    throw new Exception("Execution " + execid+ " is already running.");
	}
	
	ExecutableFlow flow = executorLoader.fetchExecutableFlow(execid);
    }
    
    
}
