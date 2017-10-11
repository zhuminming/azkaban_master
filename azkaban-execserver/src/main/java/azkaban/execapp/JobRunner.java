/**   
 * @Title: JobRunner.java 
 * @Package azkaban.execapp 
 * @Description: TODO 
 * @author minming.zhu  
 * @date 2017年9月21日 下午10:28:29 
 * @version V1.0   
 */
package azkaban.execapp;

import java.io.File;

import org.azkaban.common.executor.ExecutableFlow;
import org.azkaban.common.executor.ExecutableNode;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.jobExecutor.Job;
import org.azkaban.common.jobtype.JobTypeManager;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;

/**
 * @ClassName: JobRunner
 * @Description: TODO
 * @author minming.zhu
 * @date 2017年9月21日 下午10:28:29
 * 
 */
public class JobRunner implements Runnable{
    private ExecutorLoader loader;
    private ExecutableNode node;
    private File workingDir;
    private Job job;
    private int executionId = -1;
    private String jobId;
    private JobTypeManager jobtypeManager;
    private Props props;

    public JobRunner(ExecutableNode node, File workingDir, ExecutorLoader loader,
	      JobTypeManager jobtypeManager) {
	this.node = node;
	this.workingDir = workingDir;

	this.executionId = node.getParentFlow().getExecutionId();
	this.jobId = node.getNodename();
	this.loader = loader;
	this.jobtypeManager = jobtypeManager;
    }

    /* (非 Javadoc) 
    * <p>Title: run</p> 
    * <p>Description: </p>  
    * @see java.lang.Runnable#run() 
    */
    @Override
    public void run() {
	// TODO Auto-generated method stub
	Thread.currentThread().setName("JobRunner-" + this.jobId + "-" + executionId);
        runjob();
    }
    
    private void runjob(){
	//建立一个作业
	this.job = jobtypeManager.buildJobExecutor(jobId,props);
	try {
	    //执行作业
	    this.job.run();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
