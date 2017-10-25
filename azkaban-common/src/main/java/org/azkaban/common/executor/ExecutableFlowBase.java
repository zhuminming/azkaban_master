/**
 * ExecutableFlowBase.java
 * zmm
 * 20172017年9月20日下午9:09:18
 */
package org.azkaban.common.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azkaban.common.flow.Flow;
import org.azkaban.common.utils.TypedMapWrapper;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * @author zmm
 *
 */
public class ExecutableFlowBase extends ExecutableNode {
    public static final String flow_id_PARAM = "flow_id";
    public static final String NODES_PARAM = "nodes";
    public static final String PROPERTIES_PARAM = "properties";
    public static final String SOURCE_PARAM = "source";
    private HashMap<String, ExecutableNode> executableNodes = Maps.newHashMap();
    private String flow_id;

    public void fillExecutableFromMapObject(
	    TypedMapWrapper<String, Object> flowObj) {
	this.flow_id = flowObj.getString(flow_id_PARAM);
	List<Object> lists = flowObj.getExecNodeLists(NODES_PARAM);
	if (lists == null)
	    return;
	for (Object node : lists) {
	    TypedMapWrapper<String, Object> nodeMap = new TypedMapWrapper<String, Object>(
		    JSONObject.parseObject((String) node, Map.class));
	    ExecutableNode executableNode = new ExecutableNode();
	    executableNode.fillExecutableFromMapObject(nodeMap);
	    executableNode.setParentFlow(this);
	    executableNodes.put(executableNode.getNodename(), executableNode);
	}
    }

    @Override
    public Map<String, Object> toObject() {
	Map<String, Object> mapObj = new HashMap<String, Object>();
	fillMapFromExecutable(mapObj);
	return mapObj;
    }

    @Override
    protected void fillMapFromExecutable(Map<String, Object> flowObjMap) {
	super.fillMapFromExecutable(flowObjMap);

	flowObjMap.put(flow_id_PARAM, flow_id);

	ArrayList<Object> nodes = new ArrayList<Object>();
	for (ExecutableNode node : executableNodes.values()) {
	    nodes.add(node.toObject());
	}
	flowObjMap.put(NODES_PARAM, nodes);
    }

    public void setFlow(Flow flow){
	this.flow_id = flow.getFlow_name();
    }
    
    public ExecutableFlowBase() {
    }

    public int getExecutionId() {
	if (this.getParentFlow() != null) {
	    return this.getParentFlow().getExecutionId();
	}

	return -1;
    }

    public int getProjectId() {
	if (this.getParentFlow() != null) {
	    return this.getParentFlow().getProjectId();
	}

	return -1;
    }

    public String getProjectName() {
	if (this.getParentFlow() != null) {
	    return this.getParentFlow().getProjectName();
	}

	return null;
    }

    public int getVersion() {
	if (this.getParentFlow() != null) {
	    return this.getParentFlow().getVersion();
	}

	return -1;
    }

    public HashMap<String, ExecutableNode> getExecutableNodes() {
	return executableNodes;
    }

    public void setExecutableNodes(
	    HashMap<String, ExecutableNode> executableNodes) {
	this.executableNodes = executableNodes;
    }

    public String getflow_id() {
	return flow_id;
    }

    public void setflow_id(String flow_id) {
	this.flow_id = flow_id;
    }

}
