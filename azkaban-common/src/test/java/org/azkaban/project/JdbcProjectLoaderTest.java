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
import org.azkaban.common.flow.Flow;
import org.azkaban.common.node.Node;
import org.azkaban.common.project.JdbcProjectLoador;
import org.azkaban.common.project.Project;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;
import org.junit.Test;

/** 
 * @ClassName: JdbcProjectLoaderTest 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年10月17日 下午9:45:10 
 *  
 */
public class JdbcProjectLoaderTest {
    private String filePath ="E:/workspace/azkaban-master/azkaban/azkaban-execserver/src/main/resources/azkaban.properties";
    private Props props = new Props(null, filePath);
    private ProjectLoader loador = new JdbcProjectLoador(props);
//    @Test
//    public void createNewProjectTest(){
//	try {
//	    loador.createNewProject("project4");
//	} catch (Exception e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//    }
    
    @Test
    public void createNewFlowTest(){
	try{
	    String projectName ="project3";
	    Flow flow = new Flow("flow1");
	    Project project = loador.fetchPoject(projectName);
	    loador.createNewFlow(flow,project);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    
//    @Test
//    public void createNewJobTest(){
//	try{
//	    String projectName ="project3";
//	    Project project = loador.fetchPoject(projectName);
//	    Flow flow = loador.fetchFlow(project.getId(),"flow1");
//	    Node node = new Node(project.getId(),flow.getFlow_name(),"node1");
//	    loador.createNewNode(node, flow, project);
//	}catch(Exception e){
//	    e.printStackTrace();
//	}
//    }
    

    
}
