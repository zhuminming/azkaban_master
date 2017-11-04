/**   
* @Title: JdbcExecutorLoaderTest.java 
* @Package org.azkaban.executor 
* @Description: TODO 
* @author minming.zhu  
* @date 2017年10月25日 下午10:25:22 
* @version V1.0   
*/
package org.azkaban.executor;

import org.azkaban.common.executor.ExecutableFlow;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.flow.Flow;
import org.azkaban.common.project.JdbcProjectLoador;
import org.azkaban.common.project.Project;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;
import org.junit.Test;

/** 
 * @ClassName: JdbcExecutorLoaderTest 
 * @Description: TODO
 * @author minming.zhu 
 * @date 2017年10月25日 下午10:25:22 
 *  
 */
public class JdbcExecutorLoaderTest {
    private String filePath ="E:/workspace/azkaban-master/azkaban/azkaban-execserver/src/main/resources/azkaban.properties";
    private Props props = new Props(null, filePath);
    private ExecutorLoader loador = new JdbcExecutorLoader(props);
    private ProjectLoader projectLoader = new JdbcProjectLoador(props);
    @Test
    public void uploadExecutableFlow(){
	try {
	    Project project = projectLoader.fetchPoject("project3");
	    Flow flow = projectLoader.fetchFlow(project.getId(), "flow1");
            ExecutableFlow execflow = new ExecutableFlow(project, flow);
	    loador.uploadExecutableFlow(execflow);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
//    @Test
//    public void updateExecutableFlow(){
//	try {
//	    int execid=0;
//	    ExecutableFlow execflow = loador.fetchExecutableFlow(execid);
//	    loador.updateExecutableFlow(execflow);
//	} catch (Exception e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//    }
}
