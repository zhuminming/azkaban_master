/**   
* @Title: ExecutorManager.java 
* @Package azkaban.execapp 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月21日 下午10:31:05 
* @version V1.0   
*/
package azkaban.execapp;

import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;

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
    * @throws 
    */
    public void submitFlow(int execid) {
	// TODO Auto-generated method stub
	
    }
    
    
}
