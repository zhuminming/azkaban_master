package org.azkaban.common.flow;

public class Flow {
	private int project_id;
	private int version;
	private final String flow_name;
	private Long modifiedTime;
	
	public Flow(String flow_name){
		this.flow_name = flow_name;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFlow_name() {
		return flow_name;
	}

	public Long getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	
}
