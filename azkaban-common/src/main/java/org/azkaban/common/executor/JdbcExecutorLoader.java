/**
 * JdbcExecutorLoader.java
 * zmm
 * 20172017年9月20日下午9:04:48
 */
package org.azkaban.common.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.azkaban.common.database.AzkabanDataSource;
import org.azkaban.common.utils.Props;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * @author zmm
 *
 */
public class JdbcExecutorLoader implements ExecutorLoader{

    private AzkabanDataSource datasource;
    public JdbcExecutorLoader(Props props){
	datasource = AzkabanDataSource.getDataSource(props);
    }
    
    /* (非 Javadoc) 
    * <p>Title: fetchExecutableFlow</p> 
    * <p>Description: </p> 
    * @param execid
    * @return
    * @throws Exception 
    * @see org.azkaban.common.executor.ExecutorLoader#fetchExecutableFlow(int) 
    */
    public ExecutableFlow fetchExecutableFlow(int execid) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	FetchExecutableFlows flowHandler = new FetchExecutableFlows();
	List<ExecutableFlow> list = runner.query(FetchExecutableFlows.FETCH_EXECUTABLE_FLOW, flowHandler, execid);
	return null;
    }

    
    private static class FetchExecutableFlows implements ResultSetHandler<List<ExecutableFlow>>{
	private final  static String FETCH_EXECUTABLE_FLOW ="SELECT exec_id, flow_data FROM execution_flows "+ "WHERE exec_id=?";

	/* (非 Javadoc) 
	* <p>Title: handle</p> 
	* <p>Description: </p> 
	* @param rs
	* @return
	* @throws SQLException 
	* @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet) 
	*/
	public List<ExecutableFlow> handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    List<ExecutableFlow> flowsList = Lists.newArrayList();
	    do{
		    int exec_id = rs.getInt(1);
		    byte[] data = rs.getBytes(2);
		    if(data!=null){
			
		    }
		    

	    }while(rs.next());
	    return null;
	}
    
    }


    /* (非 Javadoc) 
    * <p>Title: updateExecutableFlow</p> 
    * <p>Description: </p> 
    * @param time
    * @throws Exception 
    * @see org.azkaban.common.executor.ExecutorLoader#updateExecutableFlow(long) 
    */
    public void updateExecutableFlow(ExecutableFlow flow) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	final String UPDATE_EXECUTABLE_FLOW_DATA="UPDATE execution_flows "
	            + "SET status=?,update_time=?,start_time=?,end_time=?,flow_data=? "
	            + "WHERE exec_id=?";
	Map<String, Object> maps =flow.toObject();
	int result = runner.update(UPDATE_EXECUTABLE_FLOW_DATA,  flow.getStatus().getNumVal(), flow.getUpdateTime(), flow.getStartTime(), flow.getEndTime(), JSONObject.toJSON(maps), flow.getExecutionId());
	if(result ==0){
	    throw new Exception();
	}
    }
}
