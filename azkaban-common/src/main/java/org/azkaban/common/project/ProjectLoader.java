/**   
* @Title: ProjectLoader.java 
* @Package org.azkaban.common.project 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年9月21日 下午10:38:27 
* @version V1.0   
*/
package org.azkaban.common.project;

import org.azkaban.common.flow.Flow;

/** 
 * @ClassName: ProjectLoader 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年9月21日 下午10:38:27 
 *  
 */
public interface ProjectLoader {
    public Project createNewProject(String projectName) throws Exception;
    public Flow createNewFlow(Flow flow,Project project) throws Exception;
    public int getLatestProjectVersion(Project project) throws Exception;
    public Project fetchPoject(String projectName) throws Exception;
}
