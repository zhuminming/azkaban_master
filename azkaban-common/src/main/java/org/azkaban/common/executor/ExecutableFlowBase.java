/**
 * ExecutableFlowBase.java
 * zmm
 * 20172017年9月20日下午9:09:18
 */
package org.azkaban.common.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.azkaban.common.utils.TypedMapWrapper;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * @author zmm
 *
 */
public class ExecutableFlowBase {
    public static final String FLOW_ID_PARAM = "flow_name";
    public static final String NODES_PARAM = "nodes";
    public static final String PROPERTIES_PARAM = "properties";
    public static final String SOURCE_PARAM = "source";
    private HashMap<String, ExecutableNode> executableNodes =Maps.newHashMap();
    private String flow_name;

    public void fillExecutableFromMapObject(TypedMapWrapper<String, Object> flowObj) {
	this.flow_name = flowObj.getString(FLOW_ID_PARAM);
	List<Object> lists = flowObj.getExecNodeLists(NODES_PARAM);
	if(lists==null) return;
	for(Object node : lists){
	    TypedMapWrapper<String, Object> nodeMap =new  TypedMapWrapper<String, Object>(JSONObject.parseObject((String) node, Map.class));
	    ExecutableNode executableNode = new ExecutableNode();
	    executableNode.fillExecutableFromMapObject(nodeMap);
	    executableNode.setParentFlow(this);
	    executableNodes.put(executableNode.getNodename(), executableNode);
	}
    }

    public HashMap<String, ExecutableNode> getExecutableNodes() {
        return executableNodes;
    }

    public void setExecutableNodes(HashMap<String, ExecutableNode> executableNodes) {
        this.executableNodes = executableNodes;
    }

    public String getFlow_name() {
        return flow_name;
    }

    public void setFlow_name(String flow_name) {
        this.flow_name = flow_name;
    }
    
}
