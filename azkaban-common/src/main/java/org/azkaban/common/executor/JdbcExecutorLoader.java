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
import org.azkaban.common.utils.JsonUtils;
import org.azkaban.common.utils.Props;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * @author zmm
 *
 */
public class JdbcExecutorLoader implements ExecutorLoader {

    private AzkabanDataSource datasource;

    public JdbcExecutorLoader(Props props) {
	datasource = AzkabanDataSource.getDataSource(props);
    }

    /*
     * (非 Javadoc) <p>Title: fetchExecutableFlow</p> <p>Description: </p>
     * 
     * @param execid
     * 
     * @return
     * 
     * @throws Exception
     * 
     * @see org.azkaban.common.executor.ExecutorLoader#fetchExecutableFlow(int)
     */
    public ExecutableFlow fetchExecutableFlow(int execid) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	FetchExecutableFlows flowHandler = new FetchExecutableFlows();
	List<ExecutableFlow> list = runner.query(FetchExecutableFlows.FETCH_EXECUTABLE_FLOW, flowHandler,execid);
	if (list.size() > 0) {
	    return list.get(0);
	} else {
	    return null;
	}
    }

    private static class FetchExecutableFlows implements ResultSetHandler<List<ExecutableFlow>> {
	private final static String FETCH_EXECUTABLE_FLOW = "SELECT exec_id, flow_data FROM execution_flows "
		+ "WHERE exec_id=?";
	/*
	 * (非 Javadoc) <p>Title: handle</p> <p>Description: </p>
	 * 
	 * @param rs
	 * 
	 * @return
	 * 
	 * @throws SQLException
	 * 
	 * @see
	 * org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet
	 * )
	 */
	public List<ExecutableFlow> handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    List<ExecutableFlow> flowsList = Lists.newArrayList();
	    while (rs.next()){
		int execid = rs.getInt(1);
		byte[] data = rs.getBytes(2);
		if (data != null) {
		    try{
			String json =new String(data);
			ExecutableFlow flow = ExecutableFlow.createExecutableFlowFromJson(json);
			flowsList.add(flow);
		    }catch(Exception e){
			throw new SQLException("Error retrieving flow data " + execid, e);
		    }
		}
	    } ;
	    return flowsList;
	}
    }

    /*
     * (非 Javadoc) <p>Title: updateExecutableFlow</p> <p>Description: </p>
     * 
     * @param time
     * 
     * @throws Exception
     * 
     * @see
     * org.azkaban.common.executor.ExecutorLoader#updateExecutableFlow(long)
     */
    public void updateExecutableFlow(ExecutableFlow flow) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	final String UPDATE_EXECUTABLE_FLOW_DATA = "UPDATE execution_flows "
		+ "SET status=?,update_time=?,start_time=?,end_time=?,flow_data=? "
		+ "WHERE exec_id=?";
	Map<String, Object> maps = flow.toObject();
	try{
	    runner.update(UPDATE_EXECUTABLE_FLOW_DATA, flow
			.getStatus().getNumVal(), flow.getUpdateTime(), flow
			.getStartTime(), flow.getEndTime(), JSONObject.toJSON(maps),
			flow.getExecutionId());
	}catch(Exception e){
	   throw new Exception("Error updating flow.", e);
	}
    }
    
    public void uploadExecutableFlow(ExecutableFlow flow){
	QueryRunner runner = datasource.getRunner();
	final String INSERT_EXECUTABLE_FLOW_DATA="INSERT INTO execution_flows "
            + "(project_id, version, flow_id, status, submit_user, submit_time, update_time) "
            + "values (?,?,?,?,?,?,?)";
	long submitTime = System.currentTimeMillis();
	try {
	    runner.update(INSERT_EXECUTABLE_FLOW_DATA, flow.getProjectId(),flow.getVersion(),flow.getflow_id(),Status.PREPARING.getNumVal(),flow.getSubmitUser(),submitTime,submitTime);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /* (非 Javadoc) 
    * <p>Title: fetchExecutableNode</p> 
    * <p>Description: </p> 
    * @param execid
    * @return
    * @throws Exception 
    * @see org.azkaban.common.executor.ExecutorLoader#fetchExecutableNode(int) 
    */
    @Override
    public ExecutableNode fetchExecutableNode(int execid) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner =datasource.getRunner();

	FetchExecutableNodes handler= new FetchExecutableNodes();
	List<ExecutableNode> lists = runner.query(FetchExecutableNodes.FETCH_EXECUTABLE_NODE, handler,execid);
	if(lists.size()==0){
	    throw new Exception();
	}
	return lists.get(0);
    }

    /* (非 Javadoc) 
    * <p>Title: updateExecutableNode</p> 
    * <p>Description: </p> 
    * @param node
    * @throws Exception 
    * @see org.azkaban.common.executor.ExecutorLoader#updateExecutableNode(org.azkaban.common.executor.ExecutableNode) 
    */
    @Override
    public void updateExecutableNode(ExecutableNode node) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	final String UPDATE_EXECUTABLE_NODE_DATA = "UPDATE execution_nodes "
		+ "SET status=?,update_time=?,start_time=?,end_time=?,command=? "
		+ "WHERE exec_id=?";
	Map<String, Object> maps = node.toObject();
	try{
	    runner.update(UPDATE_EXECUTABLE_NODE_DATA, node
			.getStatus().getNumVal(), node.getUpdateTime(), node
			.getStartTime(), node.getEndTime(), JsonUtils.toJSON(node),
			node.getExec_id());
	}catch(Exception e){
	   throw new Exception("Error updating flow.", e);
	}
    }

    /* (非 Javadoc) 
    * <p>Title: uploadExecutableNode</p> 
    * <p>Description: </p> 
    * @param node
    * @throws Exception 
    * @see org.azkaban.common.executor.ExecutorLoader#uploadExecutableNode(org.azkaban.common.executor.ExecutableNode) 
    */
    @Override
    public void uploadExecutableNode(ExecutableFlow flow,ExecutableNode node) throws Exception {
	// TODO Auto-generated method stub
	QueryRunner runner = datasource.getRunner();
	final String INSERT_EXECUTABLE_FLOW_DATA ="INSERT INTO execution_nodes "
	            + "(exec_id,project_id, version, flow_id, status, submit_user, submit_time, update_time) "
	            + "values (?,?,?,?,?,?,?,?)";
	Long submitTime = System.currentTimeMillis();
	int result = runner.update(INSERT_EXECUTABLE_FLOW_DATA, flow.getExecutionId(),flow.getflow_id(),flow.getVersion(),node.getNodename(),Status.PREPARING.getNumVal(),flow.getSubmitUser(),submitTime,submitTime);
        if(result == 0 ){
            throw new Exception();
        }
    }
    
    private static class FetchExecutableNodes implements ResultSetHandler<List<ExecutableNode>>{
	private final static String FETCH_EXECUTABLE_NODE = "SELECT exec_id,project_id FROM execution_nodes "
		+ "WHERE exec_id=?";

	/* (非 Javadoc) 
	* <p>Title: handle</p> 
	* <p>Description: </p> 
	* @param rs
	* @return
	* @throws SQLException 
	* @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet) 
	*/
	@Override
	public List<ExecutableNode> handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    List< ExecutableNode> nodelists =Lists.newArrayList();
	    while(rs.next()){
		int exex_id = rs.getInt(1);
	    }
	    return null;
	}
    }
}
