/**   
 * @Title: JobTypeManager.java 
 * @Package org.azkaban.common.jobtype 
 * @Description: TODO 
 * @author minming.zhu  
 * @date 2017年9月26日 下午8:59:15 
 * @version V1.0   
 */
package org.azkaban.common.jobtype;

import org.apache.log4j.Logger;
import org.azkaban.common.jobExecutor.Job;
import org.azkaban.common.jobExecutor.ProcessJob;
import org.azkaban.common.utils.Props;
import org.azkaban.common.utils.Utils;

/**
 * @ClassName: JobTypeManager
 * @Description: TODO
 * @author minming.zhu
 * @date 2017年9月26日 下午8:59:15
 * 
 */
public class JobTypeManager {
    private final static Logger logger = Logger.getLogger(JobTypeManager.class);
    private JobTypePluginSet pluginSet;

    public JobTypeManager(String jobtypePluginDir, Props globalProperties,
	    ClassLoader parentClassLoader) {
	loadPlugins();
    }

    public void loadPlugins() {
	JobTypePluginSet plugins = new JobTypePluginSet();
	loadDefaultTypes(plugins);
	pluginSet=plugins;
    }

    private void loadDefaultTypes(JobTypePluginSet plugins){
	logger.info("Loading plugin default job types");
	plugins.addPluginClass("command", ProcessJob.class);
    }

    public Job buildJobExecutor(String jobId,Props jobProps) {
	final JobTypePluginSet pluginSet = getJobTypePluginSet();
	String jobType = jobProps.getString("type");
	Class<? extends Object> executorClass = pluginSet.getPluginClass(jobType);
	Job job = (Job) Utils.callConstructor(executorClass, jobId, null,jobProps, logger);
	return job;
    }

    private JobTypePluginSet getJobTypePluginSet() {
	return this.pluginSet;

    }
}
