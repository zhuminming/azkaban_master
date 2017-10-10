package org.azkaban.common.jobExecutor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.azkaban.common.jobExecutor.process.AzkabanProcess;
import org.azkaban.common.jobExecutor.process.AzkabanProcessBuilder;
import org.azkaban.common.utils.Props;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * A job that runs a simple unix command
 */
public class ProcessJob extends AbstractProcessJob {
    public static final String COMMAND = "command";
    private static final long KILL_TIME_MS = 5000;
    private volatile AzkabanProcess process;

    public ProcessJob(String jobid, Props sysProps, Props jobProps, Logger log) {
	super(jobid, sysProps, jobProps, log);
	// TODO Auto-generated constructor stub
    }

    public double getProcess() {
	// TODO Auto-generated method stub
	return 0;
    }

    public boolean isCancle() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void run() throws Exception {
	// TODO Auto-generated method stub

	List<String> commands = getCommandList();
	Map<String, String> envVars = getEnvironmentVariables();
	for (String command : commands) {
	    AzkabanProcessBuilder builder = new AzkabanProcessBuilder(command)
		    .setEnvs(envVars).setWorkingDir(getWorkingDirectory())
		    .setLogger(getLog());

	    this.process = builder.build();
	    this.process.run();
	}
    }

    private List<String> getCommandList() {
	List<String> lists = Lists.newArrayList();
	lists.add(jobProps.getString(COMMAND));
	for (int i = 1; jobProps.containsKey(COMMAND + "." + i); i++) {
	    lists.add(jobProps.getString(COMMAND + "." + i));
	}
	return lists;
    }

}
