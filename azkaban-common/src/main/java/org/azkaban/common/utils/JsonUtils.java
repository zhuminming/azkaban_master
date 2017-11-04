/**
 * JsonUtils.java
 * zmm
 * 20172017年9月18日下午9:38:58
 */
package org.azkaban.common.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * @author zmm
 *
 */
public class JsonUtils {
	
	public static Map<String, Object> parserStringToMap(String json){
		JSONObject jsonObject = JSONObject.parseObject(json);
		Map<String, Object> maps =Maps.newHashMap();
		maps.put(Constant.NODE_PROJECT_ID, jsonObject.get(Constant.NODE_PROJECT_ID));
		maps.put(Constant.NODE_FLOW_NAME, jsonObject.get(Constant.NODE_FLOW_NAME));
		maps.put(Constant.NODE_NAME, jsonObject.get(Constant.NODE_NAME));
		maps.put(Constant.NODE_JOBSOURCE, jsonObject.get(Constant.NODE_JOBSOURCE));
		maps.put(Constant.NODE_JOBTYPE, jsonObject.get(Constant.NODE_JOBTYPE));
		maps.put(Constant.NODE_LEVEL, jsonObject.get(Constant.NODE_LEVEL));
		maps.put(Constant.NODE_PARENT_NAME, jsonObject.get(Constant.NODE_PARENT_NAME));
		maps.put(Constant.NODE_EXPECTED_RUNTIMME, jsonObject.get(Constant.NODE_EXPECTED_RUNTIMME));
		return maps;
	}
	
	
	public static String toJSON(Object obj){
		return JSONObject.toJSONString(obj);
	}

}
