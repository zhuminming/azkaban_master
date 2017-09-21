/**
 * ExecutableFlow.java
 * zmm
 * 20172017年9月20日下午9:08:55
 */
package org.azkaban.common.executor;

/**
 * @author zmm
 *
 */
public class ExecutableFlow extends ExecutableFlowBase {
	private int executionId = -1;
	private int scheduleId = -1;
	private int projectId;
	private String projectName;
	private String lastModifiedUser;
	private int version;
	private long submitTime = -1;
	private long lastModifiedTimestamp;
	private String submitUser;
	private String executionPath;

}
