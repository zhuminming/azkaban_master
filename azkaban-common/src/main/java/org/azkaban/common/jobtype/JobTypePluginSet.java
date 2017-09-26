/**   
* @Title: JobTypePluginSet.java 
* @Package org.azkaban.common.jobtype 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月26日 下午9:05:07 
* @version V1.0   
*/
package org.azkaban.common.jobtype;

import java.util.HashMap;
import java.util.Map;

import org.azkaban.common.jobExecutor.Job;
import org.azkaban.common.jobExecutor.ProcessJob;
import org.azkaban.common.utils.Props;

/** 
 * @ClassName: JobTypePluginSet 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年9月26日 下午9:05:07 
 *  
 */
public class JobTypePluginSet {
    private Map<String, Class<? extends Job>> jobToClass;
    private Map<String, Props> pluginJobPropsMap;

    private Props commonJobProps;
    /**
     * Base constructor
     */
    public JobTypePluginSet() {
      jobToClass = new HashMap<String, Class<? extends Job>>();
      pluginJobPropsMap = new HashMap<String, Props>();
    }
    
    /** 
     * @Title: addPluginClass 
     * @Description: TODO 
     * @param @param string
     * @param @param class1  
     * @return void 
     * @throws 
     */
     public void addPluginClass(String string, Class<? extends Job> jobTypeClass) {
 	// TODO Auto-generated method stub
 	this.jobToClass.put(string, jobTypeClass);
     }
     
    public Map<String, Class<? extends Job>> getJobToClass() {
        return jobToClass;
    }
    public void setJobToClass(Map<String, Class<? extends Job>> jobToClass) {
        this.jobToClass = jobToClass;
    }
    public Map<String, Props> getPluginJobPropsMap() {
        return pluginJobPropsMap;
    }
    public void setPluginJobPropsMap(Map<String, Props> pluginJobPropsMap) {
        this.pluginJobPropsMap = pluginJobPropsMap;
    }
    public Props getCommonJobProps() {
        return commonJobProps;
    }
    public void setCommonJobProps(Props commonJobProps) {
        this.commonJobProps = commonJobProps;
    }

    /** 
    * @Title: getPluginClass 
    * @Description: TODO 
    * @param @param jobType
    * @param @return  
    * @return Class<? extends Object>
    * @throws 
    */
    public Class<? extends Object> getPluginClass(String jobType) {
	// TODO Auto-generated method stub
	return jobToClass.get(jobType)==null?null:jobToClass.get(jobType);
    }
    
}
