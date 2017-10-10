/**   
 * @Title: ExecutorManager.java 
 * @Package org.azkaban.common.executor 
 * @Description: TODO 
 * @author minming.zhu  
 * @date 2017年10月10日 下午9:48:11 
 * @version V1.0   
 */
package org.azkaban.common.executor;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: ExecutorManager
 * @Description: TODO
 * @author minming.zhu
 * @date 2017年10月10日 下午9:48:11
 * 
 */
public class ExecutorManager {
    private ExecutorLoader executorLoader;

    public String submitExecutableFlow(ExecutableFlow exflow) {
	try {
	    executorLoader.updateExecutableFlow(exflow);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }


}
