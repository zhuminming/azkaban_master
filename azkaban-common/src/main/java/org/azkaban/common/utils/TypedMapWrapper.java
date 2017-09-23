/**   
* @Title: TypedMapWrapper.java 
* @Package org.azkaban.common.utils 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月23日 下午2:23:27 
* @version V1.0   
*/
package org.azkaban.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.azkaban.common.executor.ExecutableNode;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/** 
 * @ClassName: TypedMapWrapper 
 * @Description: TODO
 * @author minming.zhu 
 * @param <V>
 * @date 2017年9月23日 下午2:23:27 
 *  
 */
public class TypedMapWrapper<K,V> {
    private Map<K,V> map;
    public  TypedMapWrapper(Map map){
	this.map = map;
    }
    public String getString(K k){ return getString(k, null);}
    
    public String getString(K k,String defaultVal){
	Object obj = map.get(k);
	if(obj == null){
	    return defaultVal;
	}
	if(obj instanceof String){
	    return (String)obj;
	}
	return obj.toString();
    }
    
    public int getInt(K k){return getInt(k,-1);}
    
    public int getInt(K k,Integer defalutVal){
	Object obj = map.get(k);
	if(obj==null){
	    return defalutVal;
	}
	if(obj instanceof Integer){
	    return (Integer) obj;
	}else if(obj instanceof String){
	    return Integer.parseInt((String)obj);
	}
	
	return defalutVal;
    }
    
    public long getLong(K k){return getLong(k,0L);}
    
    public long getLong(K k ,Long defalutVal){
	Object obj = map.get(k);
	if(obj==null){
	    return defalutVal;
	}
	if(obj instanceof Integer){
	    return (Integer) obj;
	}else if(obj instanceof String){
	    return Long.parseLong((String)obj);
	}
	return defalutVal;
    }
    
    public Collection<String> getStringCollection(
	    String innodesParam, Set<String> defalutSet) {
	// TODO Auto-generated method stub
	Object obj = map.get(innodesParam);
	Set<String> sets = Sets.newHashSet();
	if(obj == null){
	    return defalutSet;
	}
	return JSONObject.parseObject((String) obj, Set.class);
    }
    
    
    public List<Object> getExecNodeLists(String param){
	Object obj = this.map.get(param);
	if(obj ==null){
	    return Lists.newArrayList();
	}
	return JSONObject.parseObject((String)obj, List.class);
    }

}
