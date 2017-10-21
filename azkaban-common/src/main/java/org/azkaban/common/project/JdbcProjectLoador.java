/**
 * JdbcProjectLoador.java
 * zmm
 * 20172017年9月14日下午10:04:31
 */
package org.azkaban.common.project;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;








import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.azkaban.DB.User;
import org.azkaban.common.database.AzkabanDataSource;
import org.azkaban.common.flow.Flow;
import org.azkaban.common.node.Node;
import org.azkaban.common.utils.Constant;
import org.azkaban.common.utils.JsonUtils;
import org.azkaban.common.utils.Props;

import com.alibaba.fastjson.serializer.DateSerializer;
import com.google.common.collect.Lists;

/**
 * @author zmm
 *
 */
public class JdbcProjectLoador implements ProjectLoader {
    private static final Logger logger = Logger.getLogger(JdbcProjectLoador.class);
    private AzkabanDataSource datasource;

    public JdbcProjectLoador(Props props) {
	datasource = AzkabanDataSource.getDataSource(props);
    }

    public Project createNewProject(String projectName) throws Exception {
	Connection connection = getConnection();
	Project project = createNewProject(connection, projectName);
	return project;
    }
    
    public Flow createNewFlow(Flow flow,Project project) throws Exception{
	Connection connection = getConnection();
	int version = getLatestProjectVersion(project)+1;
	return createNewFlow(connection, version, flow, project);
    }

    private Connection getConnection() {
	Connection connection = null;
	try {
	    connection = datasource.getConnection();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return connection;
    }

    private synchronized Project createNewProject(Connection connect,String projectName) throws Exception {
	QueryRunner runner = new QueryRunner();
	if (!getAvailableProjects(connect, projectName).isEmpty()) {
	    throw new Exception("Active project with name " + projectName + " already exists in db.");
	}
	final String INSERT_PROJECT = "INSERT INTO projects ( name, active, modified_time, create_time, version, last_modified_by) values (?,?,?,?,?,?)";

	int result = runner.update(connect, INSERT_PROJECT, projectName, true,
		System.currentTimeMillis(), System.currentTimeMillis(), null,
		System.currentTimeMillis());
	if (result == 0) {
	    throw new Exception();
	}
	List<Project> lists = getAvailableProjects(connect, projectName);
	if (lists.isEmpty()) {
	    throw new Exception("Active project with name " + projectName + " is not exists in db.");
	} else if (lists.size() > 1) {
	    throw new Exception("More than one active project " + projectName);
	}

	return lists.get(0);
    }

    public synchronized void changeProjectVersion(Project project, int version) throws Exception {
	QueryRunner runner = datasource.getRunner();
	long lastModifiedTimestamp = System.currentTimeMillis();
	final String UPADTE_PROJECT_VERSION = "UPDATE projects set version=?,modified_time=?,last_modified_by=? WHERE id=? ";
	int result = runner.update(UPADTE_PROJECT_VERSION, version,lastModifiedTimestamp, project.getId());
	project.setVersion(version);
	project.setModified_time(lastModifiedTimestamp);
	if (result == 0) {
	    throw new Exception();
	}
    }

    public synchronized Flow createNewFlow(Connection connect, int version,Flow flow, Project project) throws Exception {
	QueryRunner runner = new QueryRunner();
	if (!getAvailableFlows(connect, flow.getFlow_name()).isEmpty()) {
	    throw new Exception("Active Flow with name " + flow.getFlow_name() + " already exists in db.");
	}
	final String INSERT_FLOW = "INSERT INTO project_flows (project_id, version, flow_id, modified_time) values (?,?,?,?)";
	int result = runner.update(connect, INSERT_FLOW, project.getId(),version, flow.getFlow_name(),System.currentTimeMillis());
	if (result == 0) {
	    throw new Exception();
	}
	List<Flow> lists = getAvailableFlows(connect, flow.getFlow_name());
	if (lists.isEmpty()) {
	    throw new Exception("Active flow with name " + flow.getFlow_name()+ " is not exists in db.");
	} else if (lists.size() > 1) {
	    throw new Exception("More than one active flow " + flow.getFlow_name());
	}
	return lists.get(0);
    }

    public synchronized void updateFlow(Flow flow, int version, Project project) throws Exception {
	QueryRunner runner = datasource.getRunner();
	final String UDATE_FLOW = "UPDATE　project_flows set  wehere project_id = ? & flow_name = ?";
	int result = runner.update(UDATE_FLOW, version);
	if (result == 0) {
	    throw new Exception();
	}
    }

    public synchronized Node createNewNode(Connection connect, Project project,Node node, int vesrion) throws Exception {
	QueryRunner runner = datasource.getRunner();
	if (!getAvailableNodes(connect, node.getNode_name()).isEmpty()) {
	    throw new Exception("Active node with name " + node.getNode_name() + " is not exists in db.");
	}
	final String INSERT_NODE = "INSERT INTO project_nodes (project_id, version, flow_name , node_name , modified_time , json) values (?,?,?,?,?,?)";
	int result = runner.update(connect, INSERT_NODE, project.getId(),
		node.getLevel(), node.getFlow_name(), node.getNode_name(),
		System.currentTimeMillis(), JsonUtils.toJSON(node));
	connect.commit();
	if (result == 0) {
	    throw new Exception();
	}
	List<Node> lists = getAvailableNodes(connect, node.getNode_name());
	if (lists.isEmpty()) {
	    throw new Exception("Active node with name " + node.getNode_name()+ " is not exists in db.");
	} else if (lists.size() > 1) {
	    throw new Exception("More than one active node "+ node.getNode_name());
	}
	return node;
    }

    /* (非 Javadoc) 
    * <p>Title: getLatestProjectVersion</p> 
    * <p>Description: </p> 
    * @param project
    * @return 
    * @see org.azkaban.common.project.ProjectLoader#getLatestProjectVersion(org.azkaban.common.project.Project) 
    */
    public int getLatestProjectVersion(Project project) throws SQLException {
	// TODO Auto-generated method stub
	QueryRunner runner =datasource.getRunner();
	int vserion = runner.query(IntHander.SELECT_LATEST_VERSION, new IntHander(), project.getName());
	return vserion;
    }
    
    private List<Project> getAvailableProjects(Connection connect,String projectName) {
	List<Project> proListsList = Lists.newArrayList();
	QueryRunner runner = new QueryRunner();
	ProjectResultHandler handler = new ProjectResultHandler();
	try {
	    proListsList = runner.query(connect, ProjectResultHandler.SELECT_ACTIVE_PROJECT_BY_NAME, handler,projectName);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return proListsList;
    }

    private List<Flow> getAvailableFlows(Connection connect, String flowname) throws Exception {
	List<Flow> flowLists = Lists.newArrayList();
	QueryRunner runner = datasource.getRunner();
	FlowResultHandler handler = new FlowResultHandler();
	flowLists = runner.query(connect,FlowResultHandler.SELECT_ACTION_FLOW_BY_PROJECT, handler,flowname);
	return flowLists;
    }

    private List<Node> getAvailableNodes(Connection connect, String nodename) throws Exception {
	List<Node> nodeLists = Lists.newArrayList();
	QueryRunner runner = datasource.getRunner();
	NodeResultHandler handler = new NodeResultHandler();
	nodeLists = runner.query(connect,NodeResultHandler.SELECT_ACTION_NODE_BY_PROJECT_FLOW, handler,nodename);
	return nodeLists;
    }
    
    private static class ProjectResultHandler implements ResultSetHandler<List<Project>> {
	private static final String SELECT_PROJECT_BY_ID = "SELECT id, name, active, modified_time, create_time, version, last_modified_by FROM projects WHERE id=?";
	private static final String SELECT_ALL_ACTIVE_PROJECTS = "SELECT id, name, active, modified_time, create_time, version, last_modified_by FROM projects WHERE active=true";
	private static final String SELECT_ACTIVE_PROJECT_BY_NAME = "select id,name, active, modified_time, create_time, version, last_modified_by from projects where name=? and active=true ";

	public List<Project> handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    List<Project> lists = Lists.newArrayList();
	    Project project;
	    while (rs.next()){
		final int id = rs.getInt(1);
		final String projectName = rs.getString(2);
		final boolean active = rs.getBoolean(3);
		final long modifiedTime = rs.getLong(4);
		final long createTime = rs.getLong(5);
		final int version = rs.getInt(6);
		final String lastModeifiedUser = rs.getString(7);
		project = new Project(id,projectName);
		project.setActive(active);
		project.setVersion(version);
		project.setCreate_time(createTime);
		project.setModified_time(modifiedTime);
		project.setLast_modeified_by(lastModeifiedUser);
		lists.add(project);
	    }
	    return lists;
	}
    }

    private class FlowResultHandler implements ResultSetHandler<List<Flow>> {
	private static final String SELECT_ACTION_FLOW_BY_PROJECT = "SELECT project_id , version ,flow_id FROM project_flows WHERE  project_id=?";

	public List<Flow> handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    Flow flow;
	    List<Flow> lists = Lists.newArrayList();
	    while (rs.next()) {
		final int project_id = rs.getInt(1);
		final int version = rs.getInt(2);
		final String flow_name = rs.getString(3);
		flow = new Flow(flow_name);
		flow.setProject_id(project_id);
		flow.setVersion(version);
		lists.add(flow);
	    }
	    return lists;
	}
    }

    private class NodeResultHandler implements ResultSetHandler<List<Node>> {
	private static final String SELECT_ACTION_NODE_BY_PROJECT_FLOW = "";
	public List<Node> handle(ResultSet rs) throws SQLException {
	    Node node;
	    List<Node> lists = Lists.newArrayList();
	    while (rs.next()){
		final int project_id = rs.getInt(1);
		final int version = rs.getInt(2);
		final String flow_name = rs.getString(3);
		final String node_name = rs.getString(4);
		final Long modifiedTime = rs.getLong(5);
		final byte[] dataBytes = rs.getBytes(6);
		try {
		    String propertyString = new String(dataBytes, "UTF-8");
		    Map<String, Object> maps = JsonUtils.parserStringToMap(propertyString);
		    node = new Node(project_id, flow_name, node_name);
		    node.setLevel(Integer.parseInt((String) maps.get(Constant.NODE_LEVEL)));
		    node.setVersion(version);
		    node.setModifiedTime(modifiedTime);
		    lists.add(node);
		} catch (UnsupportedEncodingException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    } 
	    return lists;
	}
    }

    private class IntHander implements ResultSetHandler<Integer> {

	private static final String SELECT_LATEST_VERSION ="select version from projects where name =?";
	/* (非 Javadoc) 
	* <p>Title: handle</p> 
	* <p>Description: </p> 
	* @param rs
	* @return
	* @throws SQLException 
	* @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet) 
	*/
	public Integer handle(ResultSet rs) throws SQLException {
	    // TODO Auto-generated method stub
	    int version =0;
	    while(rs.next()){
		version = rs.getInt(1);
	    }
	    return version;
	}
	
    }

    /* (非 Javadoc) 
    * <p>Title: fetchPoject</p> 
    * <p>Description: </p> 
    * @param projectName
    * @return
    * @throws Exception 
    * @see org.azkaban.common.project.ProjectLoader#fetchPoject(java.lang.String) 
    */
    public Project fetchPoject(String projectName) throws Exception {
	// TODO Auto-generated method stub
	List<Project> proListsList = Lists.newArrayList();
	QueryRunner runner = datasource.getRunner();
	ProjectResultHandler handler = new ProjectResultHandler();
	try {
	    proListsList = runner.query(ProjectResultHandler.SELECT_ACTIVE_PROJECT_BY_NAME, handler,projectName);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return proListsList.get(0);
    }

}
