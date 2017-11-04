package org.azkaban.common.node;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


public class Node {
	private  final int project_id;
	private final String flow_name;
	private int version;
	private final String node_name;
	private long modifiedTime;
	private int level=-1;
	private int expectedRunTimeSec = 1;
	private String type;
	private Set<String> parentnodes;
	
	public Node(int project_id,String flow_name,String node_name){
		this.project_id = project_id;
		this.flow_name = flow_name;
		this.node_name = node_name;
		this.parentnodes = Sets.newHashSet();
	}
	
	public int getProject_id() {
		return project_id;
	}
	public String getFlow_name() {
		return flow_name;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getNode_name() {
		return node_name;
	}
	public long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public void setNode_parent(String node_parent) {
	    this.parentnodes.add(node_parent);
	}
	
	public void setParentNodes(Set<String> nodes){
	    this.parentnodes.addAll(nodes);
	}
	
	public Set<String> getParentNodes(){
	    return this.parentnodes;
	}

	public int getExpectedRunTimeSec() {
		return expectedRunTimeSec;
	}
	public void setExpectedRunTimeSec(int expectedRunTimeSec) {
		this.expectedRunTimeSec = expectedRunTimeSec;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
