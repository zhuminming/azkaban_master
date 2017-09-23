/**
 * ExecutableFlow.java
 * zmm
 * 20172017年9月20日下午9:08:55
 */
package org.azkaban.common.executor;

import java.util.Map;

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
    public static final String LASTMODIFIEDUSER_PARAM = "lastModifiedUser";
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
	this.lastModifiedUser = mapWrapper.getString(LASTMODIFIEDUSER_PARAM);
	this.submitTime = mapWrapper.getLong(SUBMITTIME_PARAM);

    }

}
