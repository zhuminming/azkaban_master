/**   
* @Title: ExecutorManager.java 
* @Package azkaban.execapp 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月21日 下午10:31:05 
* @version V1.0   
*/
package azkaban.execapp;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(ExecutorManager.class);
    private Props props;
    private ExecutorLoader executorLoader;
    private ProjectLoader projectLoader;
    private File executionDirectory;
    private File projectDirectory;
    private ExecutorService executorService;
    private Map<Future<?>, Integer> submittedFlows =Maps.newHashMap();

    private Map<Integer, FlowRunner> runningFlows =Maps.newHashMap();
    
    public ExecutorManager(Props props ,ExecutorLoader executorLoader,ProjectLoader projectLoader){
	this.props = props;
	this.executorLoader = executorLoader;
	this.projectLoader = projectLoader;
	this.executionDirectory = new File(props.getString("azkaban.execution.dir", "executions"));
	this.projectDirectory = new File(props.getString("azkaban.project.dir", "project"));

	this.executorService = Executors.newFixedThreadPool(10);
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
	if(flow ==null){
	    throw new Exception("Error loading flow with exec " + execid);
	}
	setupFlow(flow);
	FlowRunner runner = new FlowRunner(flow ,executorLoader,projectLoader);
	if(runningFlows.containsKey(execid)){
	    throw new Exception("Execution " + execid + " is already running.");
	}
	runningFlows.put(execid, runner);
	
	Future<?> future = executorService.submit(runner);
	submittedFlows.put(future,runner.getExecId());
	
    }
    
    private void setupFlow(ExecutableFlow flow){
	int execId = flow.getExecutionId();
	File execPath = new File(executionDirectory,String.valueOf(execId));
	flow.setExecutionPath(execPath.getPath());
	logger.info("Flow " + execId + " submitted with path " + execPath.getPath());
	execPath.mkdirs();
	setupProjectFiles(flow);

    }
    
    
    private void setupProjectFiles(ExecutableFlow flow){
	File projectPath = new File(projectDirectory,flow.getProjectId()+"."+flow.getVersion());
	
	logger.info("First time executing new project. Setting up in directory "+ projectPath.getPath());
	if(!projectPath.exists()){
	    projectPath.mkdirs();
	}
	
    }
    
}
