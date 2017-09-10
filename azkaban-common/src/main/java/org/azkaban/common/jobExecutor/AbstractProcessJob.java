package org.azkaban.common.jobExecutor;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.azkaban.common.utils.Props;
import org.azkaban.common.utils.PropsUtils;

import com.google.common.collect.Maps;

/**
 * A revised process-based job
 */
public abstract class AbstractProcessJob extends AbstractJob {
	private final Logger log;
	public static final String ENV_PREFIX = "env.";
	public static final String ENV_PREFIX_UCASE = "ENV.";
	public static final String WORKING_DIR = "working.dir";
	public static final String JOB_PROP_ENV = "JOB_PROP_FILE";
	public static final String JOB_NAME_ENV = "JOB_NAME";
	public static final String JOB_OUTPUT_PROP_FILE = "JOB_OUTPUT_PROP_FILE";

	protected final String _jobPath;

	protected volatile Props jobProps;
	protected volatile Props sysProps;

	protected String _cwd;

	private volatile Props generatedProperties;

	protected AbstractProcessJob(String jobid, final Props sysProps,
			final Props jobProps, final Logger log) {
		super(jobid, log);

		this.jobProps = jobProps;
		this.sysProps = sysProps;
		_cwd = getWorkingDirectory();
		_jobPath = _cwd;

		this.log = log;
	}

	public Props getJobProps() {
		return jobProps;
	}

	public Props getSysProps() {
		return sysProps;
	}

	public String getJobPath() {
		return _jobPath;
	}

	@Override
	public Props getJobGeneratedProperties() {
		return generatedProperties;
	}

	public String getWorkingDirectory() {
		String workingDir = getJobProps().getString(WORKING_DIR, _jobPath);
		if (workingDir == null) {
			return "";
		}
		return workingDir;
	}
	
	public Map<String,String> getEnvironmentVariables(){
		return Maps.newHashMap();
	}
}
