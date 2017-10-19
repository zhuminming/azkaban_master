/**   
* @Title: JdbcProjectLoaderTest.java 
* @Package org.azkaban.project 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年10月17日 下午9:45:10 
* @version V1.0   
*/
package org.azkaban.project;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.project.JdbcProjectLoador;
import org.azkaban.common.project.Project;
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
	    loador.createNewProject("project2");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
