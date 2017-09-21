/**
 * ExecutableNode.java
 * zmm
 * 20172017年9月20日下午9:08:46
 */
package org.azkaban.common.executor;

import org.azkaban.common.utils.Props;

/**
 * @author zmm
 *
 */
public class ExecutableNode {
	private String id;
	  private String type = null;
	  private Status status = Status.READY;
	  private long startTime = -1;
	  private long endTime = -1;
	  private long updateTime = -1;

	  // Path to Job File
	  private String jobSource;
	  // Path to top level props file
	  private String propsSource;
	  private Props inputProps;
	  private Props outputProps;

	  // Transient. These values aren't saved, but rediscovered.
	  private ExecutableFlowBase parentFlow;

}
