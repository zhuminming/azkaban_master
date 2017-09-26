package org.azkaban.common.jobExecutor.process;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AzkabanProcessBuilder {
    private List<String> cmd = Lists.newArrayList();
    private Map<String, String> env = Maps.newHashMap();
    private String workingDir = System.getProperty("user.dir");
    private Logger logger = Logger.getLogger(AzkabanProcess.class);

    public AzkabanProcessBuilder(String... command) {
	addArg(command);
    }

    private void addArg(String... command) {
	for (String c : command) {
	    cmd.add(c);
	}
    }

    public AzkabanProcess build() {
	return new AzkabanProcess(cmd, env, workingDir, logger);
    }

    public AzkabanProcessBuilder setEnvs(Map<String, String> env) {
	this.env = env;
	return this;
    }

    public AzkabanProcessBuilder setWorkingDir(String workingDir) {
	this.workingDir = workingDir;
	return this;
    }

    public AzkabanProcessBuilder setLogger(Logger logger) {
	this.logger = logger;
	return this;
    }
}
