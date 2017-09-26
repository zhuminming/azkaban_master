package org.azkaban.common.jobExecutor.process;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * An improved version of java.lang.Process.
 * 
 * Output is read by separate threads to avoid deadlock and logged to log4j
 * loggers.
 */
public class AzkabanProcess {
    private final String workingDir;
    private final List<String> cmd;
    private final Map<String, String> env;
    private final Logger logger;

    private CountDownLatch startupLatch;

    private volatile int processId;
    private volatile Process process;

    public AzkabanProcess(final List<String> cmd,
	    final Map<String, String> env, final String workingDir,
	    final Logger logger) {
	this.cmd = cmd;
	this.env = env;
	this.workingDir = workingDir;
	this.processId = -1;
	this.startupLatch = new CountDownLatch(1);
	this.logger = logger;

    }

    /**
     * Execute this process, blocking until it has completed.
     */
    public void run() throws IOException {
	if (isStarted()) {
	    throw new IllegalStateException(
		    "The process can only be used once.");
	}
	ProcessBuilder builder = new ProcessBuilder(this.cmd);
	builder.directory(new File(this.workingDir)); // 设置工作目录
	builder.environment().putAll(this.env); // 设置环境
	builder.redirectErrorStream(true); // 设置标准错误与标准输出合并

	this.process = builder.start();
	this.processId = getProcessId(this.process);

    }

    private boolean isStarted() {
	return this.startupLatch.getCount() == 0L;
    }

    private int getProcessId(final Process process) {
	try {
	    Field f = process.getClass().getDeclaredField("pid");
	    f.setAccessible(true);
	    this.processId = f.getInt(process);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return this.processId;
    }
}
