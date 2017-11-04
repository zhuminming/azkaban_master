/**
 * ExecutableNode.java
 * zmm
 * 20172017年9月20日下午9:08:46
 */
package org.azkaban.common.executor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.azkaban.common.utils.Props;
import org.azkaban.common.utils.TypedMapWrapper;

/**
 * @author zmm
 *
 */
public class ExecutableNode {
    public static final String NODENAME_PARAM = "nodename";
    public static final String STATUS_PARAM = "status";
    public static final String STARTTIME_PARAM = "startTime";
    public static final String ENDTIME_PARAM = "endTime";
    public static final String UPDATETIME_PARAM = "updateTime";
    public static final String INNODES_PARAM = "inNodes";
    public static final String OUTNODES_PARAM = "outNodes";
    public static final String TYPE_PARAM = "type";
    public static final String PROPS_SOURCE_PARAM = "propSource";
    public static final String JOB_SOURCE_PARAM = "jobSource";
    private String nodename;
    private String type = null;
    private Status status = Status.READY;
    private long startTime = -1;
    private long endTime = -1;
    private long updateTime = -1;
    private int exec_id;
    private String node_data;

    // Path to Job File
    private String jobSource;
    // Path to top level props file
    private String propsSource;

    private Set<String> inNodes;
    private Set<String> outNodes;

    // Transient. These values aren't saved, but rediscovered.
    private ExecutableFlowBase parentFlow;

    public void fillExecutableFromMapObject(
	    TypedMapWrapper<String, Object> wrappedMap) {
	this.nodename = wrappedMap.getString(NODENAME_PARAM);
	this.type = wrappedMap.getString(TYPE_PARAM);
	this.status = Status.valueOf(wrappedMap.getString(STATUS_PARAM));
	this.startTime = wrappedMap.getLong(STARTTIME_PARAM);
	this.endTime = wrappedMap.getLong(ENDTIME_PARAM);
	this.updateTime = wrappedMap.getLong(UPDATETIME_PARAM);

	this.inNodes = new HashSet<String>();
	this.inNodes.addAll(wrappedMap.getStringCollection(INNODES_PARAM,
		Collections.<String> emptySet()));

	this.outNodes = new HashSet<String>();
	this.outNodes.addAll(wrappedMap.getStringCollection(OUTNODES_PARAM,
		Collections.<String> emptySet()));

	this.propsSource = wrappedMap.getString(PROPS_SOURCE_PARAM);
	this.jobSource = wrappedMap.getString(JOB_SOURCE_PARAM);
    }

    public Map<String, Object> toObject() {
	Map<String, Object> mapObj = new HashMap<String, Object>();
	fillMapFromExecutable(mapObj);

	return mapObj;
    }

    protected void fillMapFromExecutable(Map<String, Object> objMap) {
	objMap.put(NODENAME_PARAM, this.nodename);
	objMap.put(STATUS_PARAM, status.toString());
	objMap.put(STARTTIME_PARAM, startTime);
	objMap.put(ENDTIME_PARAM, endTime);
	objMap.put(UPDATETIME_PARAM, updateTime);
	objMap.put(TYPE_PARAM, type);

	if (inNodes != null && !inNodes.isEmpty()) {
	    objMap.put(INNODES_PARAM, inNodes);
	}
	if (outNodes != null && !outNodes.isEmpty()) {
	    objMap.put(OUTNODES_PARAM, outNodes);
	}
    }

    public String getNodename() {
	return nodename;
    }

    public void setNodename(String nodename) {
	this.nodename = nodename;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    public long getStartTime() {
	return startTime;
    }

    public void setStartTime(long startTime) {
	this.startTime = startTime;
    }

    public long getEndTime() {
	return endTime;
    }

    public void setEndTime(long endTime) {
	this.endTime = endTime;
    }

    public long getUpdateTime() {
	return updateTime;
    }

    public void setUpdateTime(long updateTime) {
	this.updateTime = updateTime;
    }

    public String getJobSource() {
	return jobSource;
    }

    public void setJobSource(String jobSource) {
	this.jobSource = jobSource;
    }

    public String getPropsSource() {
	return propsSource;
    }

    public void setPropsSource(String propsSource) {
	this.propsSource = propsSource;
    }

    public Set<String> getInNodes() {
	return inNodes;
    }

    public void setInNodes(Set<String> inNodes) {
	this.inNodes = inNodes;
    }

    public Set<String> getOutNodes() {
	return outNodes;
    }

    public void setOutNodes(Set<String> outNodes) {
	this.outNodes = outNodes;
    }

    public ExecutableFlowBase getParentFlow() {
	return parentFlow;
    }

    public void setParentFlow(ExecutableFlowBase parentFlow) {
	this.parentFlow = parentFlow;
    }

    public int getExec_id() {
        return exec_id;
    }

    public void setExec_id(int exec_id) {
        this.exec_id = exec_id;
    }

    public String getNode_data() {
        return node_data;
    }

    public void setNode_data(String node_data) {
        this.node_data = node_data;
    }
    
    

}
