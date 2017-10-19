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
	private int id;
	private String name;
	private boolean active = true;
	private int version = -1;
	private long create_time;
	private long Modified_time;
	private String last_modeified_by;
	
	public Project(int id , String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
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

	public long getCreate_time() {
	    return create_time;
	}

	public void setCreate_time(long create_time) {
	    this.create_time = create_time;
	}

	public long getModified_time() {
	    return Modified_time;
	}

	public void setModified_time(long modified_time) {
	    Modified_time = modified_time;
	}

	public String getLast_modeified_by() {
	    return last_modeified_by;
	}

	public void setLast_modeified_by(String last_modeified_by) {
	    this.last_modeified_by = last_modeified_by;
	}

}
