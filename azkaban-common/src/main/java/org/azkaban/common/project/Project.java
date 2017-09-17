/**
 * Project.java
 * zmm
 * 20172017年9月14日下午10:40:46
 */
package org.azkaban.common.project;

/**
 * @author zmm
 *
 */
public class Project {
	private final int id;
	private final String name;
	private boolean active = true;
	private int version = -1;
	private long createTimestamp;
	private long lastModifiedTimestamp;
	private String lastModeifiedUser;
	
	public Project(int id , String name){
		this.id = id;
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public long getLastModifiedTimestamp() {
		return lastModifiedTimestamp;
	}

	public void setLastModifiedTimestamp(long lastModifiedTimestamp) {
		this.lastModifiedTimestamp = lastModifiedTimestamp;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastModeifiedUser() {
		return lastModeifiedUser;
	}

	public void setLastModeifiedUser(String lastModeifiedUser) {
		this.lastModeifiedUser = lastModeifiedUser;
	}
	
	

}
