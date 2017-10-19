/**   
* @Title: JdbcProjectLoaderTest.java 
* @Package org.azkaban.project 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年10月17日 下午9:45:10 
* @version V1.0   
*/
package org.azkaban.project;

import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.project.JdbcProjectLoador;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;

/** 
 * @ClassName: JdbcProjectLoaderTest 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年10月17日 下午9:45:10 
 *  
 */
public class JdbcProjectLoaderTest {
    public static void main(String[] args){
	String filePath ="E:/workspace/azkaban-master/azkaban/azkaban-execserver/src/main/resources/azkaban.properties";
	Props props = new Props(null, filePath);
	ProjectLoader loador = new JdbcProjectLoador(props);
	try {
	    loador.createNewProject("project1");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
