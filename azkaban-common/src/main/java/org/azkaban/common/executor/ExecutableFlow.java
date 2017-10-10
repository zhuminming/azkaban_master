/**
 * ExecutableFlow.java
 * zmm
 * 20172017年9月20日下午9:08:55
 */
package org.azkaban.common.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.azkaban.common.flow.Flow;
import org.azkaban.common.project.Project;
import org.azkaban.common.utils.TypedMapWrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zmm
 *
 */
public class ExecutableFlow extends ExecutableFlowBase {
    public static final String EXECUTIONID_PARAM = "executionId";
    public static final String EXECUTIONPATH_PARAM = "executionPath";
    public static final String EXECUTIONOPTIONS_PARAM = "executionOptions";
    public static final String PROJECTID_PARAM = "projectId";
    public static final String SCHEDULEID_PARAM = "scheduleId";
    public static final String SUBMITUSER_PARAM = "submitUser";
    public static final String SUBMITTIME_PARAM = "submitTime";
    public static final String VERSION_PARAM = "version";
    public static final String PROXYUSERS_PARAM = "proxyUsers";
    public static final String PROJECTNAME_PARAM = "projectName";
    public static final String LASTMODIFIEDTIME_PARAM = "lastModfiedTime";
    private int executionId = -1;
    private int scheduleId = -1;
    private int projectId;
    private String projectName;
    private int version;
    private Status status = Status.READY;
    private long submitTime = -1;
    private long lastModifiedTimestamp;
    private String submitUser;
    private String executionPath;
    
    public ExecutableFlow(){}
    public ExecutableFlow(Project project,Flow flow){
	this.projectId = project.getId();
	this.projectName = project.getName();
	this.version = project.getVersion();
	this.lastModifiedTimestamp = project.getLastModifiedTimestamp();
    }

    public static ExecutableFlow createExecutableFlowFromJson(String json) {
	Map<String, Object> flowObj = JSONObject.parseObject(json, Map.class);
	TypedMapWrapper<String, Object> mapWrapper = new TypedMapWrapper<String, Object>(flowObj);
	ExecutableFlow flow = new ExecutableFlow();
	flow.fillExecutableFromMapObject(mapWrapper);
	return flow;
    }

    @Override
    public void fillExecutableFromMapObject(TypedMapWrapper<String, Object> mapWrapper) {
	super.fillExecutableFromMapObject(mapWrapper);
	this.executionId = mapWrapper.getInt(EXECUTIONID_PARAM);
	this.executionPath = mapWrapper.getString(EXECUTIONPATH_PARAM);

	this.projectId = mapWrapper.getInt(PROJECTID_PARAM);
	this.projectName = mapWrapper.getString(PROJECTNAME_PARAM);
	this.version = mapWrapper.getInt(VERSION_PARAM);
	this.lastModifiedTimestamp = mapWrapper.getLong(LASTMODIFIEDTIME_PARAM);
	this.submitTime = mapWrapper.getLong(SUBMITTIME_PARAM);
    }

    @Override
    public Map<String, Object> toObject() {
      HashMap<String, Object> flowObj = new HashMap<String, Object>();
      fillMapFromExecutable(flowObj);

      flowObj.put(EXECUTIONID_PARAM, executionId);
      flowObj.put(EXECUTIONPATH_PARAM, executionPath);
      flowObj.put(PROJECTID_PARAM, projectId);
      flowObj.put(PROJECTNAME_PARAM, projectName);

      if (scheduleId >= 0) {
        flowObj.put(SCHEDULEID_PARAM, scheduleId);
      }

      flowObj.put(SUBMITUSER_PARAM, submitUser);
      flowObj.put(VERSION_PARAM, version);
      flowObj.put(LASTMODIFIEDTIME_PARAM, lastModifiedTimestamp);

      flowObj.put(VERSION_PARAM, version);

      flowObj.put(SUBMITTIME_PARAM, submitTime);

      return flowObj;
    }
    
    public int getExecutionId() {
        return executionId;
    }

    public void setExecutionId(int executionId) {
        this.executionId = executionId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(long lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public String getExecutionPath() {
        return executionPath;
    }

    public void setExecutionPath(String executionPath) {
        this.executionPath = executionPath;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
