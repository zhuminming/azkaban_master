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

import org.apache.log4j.Logger;
import org.azkaban.common.executor.ExecutableFlow;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.jobtype.JobTypeManager;
import org.azkaban.common.project.JdbcProjectLoador;
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
public class FlowRunnerManager {
    private static final Logger logger = Logger.getLogger(FlowRunnerManager.class);
    private Props props;
    private ExecutorLoader executorLoader;
    private ProjectLoader projectLoader;
    private File executionDirectory;
    private File projectDirectory;
    private ExecutorService executorService;
    private JobTypeManager jobtypeManager;
    private Map<Future<?>, Integer> submittedFlows =Maps.newHashMap();

    private Map<Integer, FlowRunner> runningFlows =Maps.newHashMap();
    
    public FlowRunnerManager(Props props ,ExecutorLoader executorLoader,ProjectLoader projectLoader){
	this.props = props;
	this.executorLoader = executorLoader;
	this.projectLoader = projectLoader;
	this.executionDirectory = new File(props.getString("azkaban.execution.dir", "executions"));
	this.projectDirectory = new File(props.getString("azkaban.project.dir", "project"));

	this.executorService = Executors.newFixedThreadPool(10);
	
	this.jobtypeManager = new JobTypeManager();
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
	//判断execid是否已经执行
	if(runningFlows.containsKey(execid)){
	    throw new Exception("Execution " + execid+ " is already running.");
	}
	
	//通过execid从execution_flows表中获取可执行的flow
	ExecutableFlow flow = executorLoader.fetchExecutableFlow(execid);
	if(flow ==null){
	    throw new Exception("Error loading flow with exec " + execid);
	}
	setupFlow(flow);
	//初始化FlowRunner线程
	FlowRunner runner = new FlowRunner(flow, executorLoader, projectLoader, jobtypeManager);
	if(runningFlows.containsKey(execid)){
	    throw new Exception("Execution " + execid + " is already running.");
	}
	//将FlowRuuner对象插入Map中
	runningFlows.put(execid, runner);
	
	//加入线程池队列中
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
    
    public static void main(String[] args) throws Exception{
	String path = "E:/workspace/azkaban-master/azkaban/azkaban-execserver/src/main/resources/azkaban.properties";
	System.out.println(path);
	File file = new File(path);
	Props props = new Props(null,file);
	
	ProjectLoader projectLoader = new JdbcProjectLoador(props);
	ExecutorLoader executorLoader =new JdbcExecutorLoader(props);
	FlowRunnerManager flowRunner = new FlowRunnerManager(props, executorLoader, projectLoader);
	flowRunner.submitFlow(3);
    }
    
}
