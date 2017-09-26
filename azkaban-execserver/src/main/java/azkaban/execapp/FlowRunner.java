/**   
 * @Title: FlowRunner.java 
 * @Package azkaban.execapp 
 * @Description: TODO 
 * @author minming.zhu  
 * @date 2017年9月21日 下午10:28:05 
 * @version V1.0   
 */
package azkaban.execapp;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.azkaban.common.executor.ExecutableFlow;
import org.azkaban.common.executor.ExecutableNode;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.Status;
import org.azkaban.common.jobtype.JobTypeManager;
import org.azkaban.common.project.ProjectLoader;

/**
 * @ClassName: FlowRunner
 * @Description: TODO
 * @author minming.zhu
 * @date 2017年9月21日 下午10:28:05
 * 
 */
public class FlowRunner implements Runnable {
    private static final Logger logger = Logger.getLogger(FlowRunner.class);
    private ExecutorLoader executorLoader;
    private ProjectLoader projectLoader;
    private ExecutorService executorService;
    private final JobTypeManager jobtypeManager;

    private int execId;
    private File execDir;
    private final ExecutableFlow flow;
    private Thread flowRunnerThread;
    private int numJobThreads = 10;
    
    private Set<JobRunner> activeJobRunners = Collections.newSetFromMap(new ConcurrentHashMap<JobRunner, Boolean>());

    public FlowRunner(ExecutableFlow flow, ExecutorLoader executorLoader,
	    ProjectLoader projectLoader,JobTypeManager jobtypeManager) {
	this.execId = flow.getExecutionId();
	this.flow = flow;
	this.executorLoader = executorLoader;
	this.projectLoader = projectLoader;
	this.execDir = new File(flow.getExecutionPath());
	this.jobtypeManager = jobtypeManager; 
    }

    /*
     * (非 Javadoc) <p>Title: run</p> <p>Description: </p>
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	// TODO Auto-generated method stub
	if (executorService == null) {
	    executorService = Executors.newFixedThreadPool(10);
	}
	updateflow();
	try {
	    runflow();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void runflow() throws IOException {
	logger.info("Starting flows");
	runReadyJob(this.flow);
	updateflow();
    }

    private void runReadyJob(ExecutableNode node) throws IOException {
	if (flow.getStatus() == Status.READY) {
	    runExecutableNode(node);
	}
    }

    private void runExecutableNode(ExecutableNode node) throws IOException {
	node.setStatus(Status.QUEUED);
	JobRunner runner = createJobRunner(node);
	executorService.submit(runner);
	activeJobRunners.add(runner);
    }

    private JobRunner createJobRunner(ExecutableNode node){
	File path = new File(execDir,node.getJobSource());
	JobRunner jobrunner = new JobRunner(node, path.getParentFile(), executorLoader,jobtypeManager);
	return jobrunner;
    }
    
    private void updateflow() {
	updateFlow(System.currentTimeMillis());
    }

    private synchronized void updateFlow(long time) {
	flow.setUpdateTime(time);
	try {
	    executorLoader.updateExecutableFlow(flow);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public ExecutorLoader getExecutorLoader() {
	return executorLoader;
    }

    public void setExecutorLoader(ExecutorLoader executorLoader) {
	this.executorLoader = executorLoader;
    }

    public ProjectLoader getProjectLoader() {
	return projectLoader;
    }

    public void setProjectLoader(ProjectLoader projectLoader) {
	this.projectLoader = projectLoader;
    }

    public int getExecId() {
	return execId;
    }

    public void setExecId(int execId) {
	this.execId = execId;
    }

    public File getExecDir() {
	return execDir;
    }

    public void setExecDir(File execDir) {
	this.execDir = execDir;
    }

    public Thread getFlowRunnerThread() {
	return flowRunnerThread;
    }

    public void setFlowRunnerThread(Thread flowRunnerThread) {
	this.flowRunnerThread = flowRunnerThread;
    }

    public int getNumJobThreads() {
	return numJobThreads;
    }

    public void setNumJobThreads(int numJobThreads) {
	this.numJobThreads = numJobThreads;
    }

    public ExecutableFlow getFlow() {
	return flow;
    }

}
