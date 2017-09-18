/**
 * JdbcProjectLoador.java
 * zmm
 * 20172017年9月14日下午10:04:31
 */
package org.azkaban.common.database;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;
import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.flow.Flow;
import org.azkaban.common.node.Node;
import org.azkaban.common.project.Project;
import org.azkaban.common.utils.Props;

import com.google.common.collect.Lists;

/**
 * @author zmm
 *
 */
public class JdbcProjectLoador implements ExecutorLoader {
	private static final Logger logger = Logger
			.getLogger(JdbcProjectLoador.class);
	private AzkabanDataSource datasource;

	public JdbcProjectLoador(Props props) {
		datasource = AzkabanDataSource.getDataSource(props);
	}

	public synchronized Project createNewProject(Connection connect,
			String projectName) throws Exception {
		QueryRunner runner = datasource.getRunner();
		if (!getAvailableProjects(connect, projectName).isEmpty()) {
			throw new Exception("Active project with name " + projectName
					+ " already exists in db.");
		}
		final String INSERT_PROJECT = "INSERT INTO projects ( name, active, modified_time, create_time, version, last_modified_by) values (?,?,?,?,?,?)";

		int result = runner.update(INSERT_PROJECT, projectName, true,
				System.currentTimeMillis(), System.currentTimeMillis(), null,
				System.currentTimeMillis());
		connect.commit();
		if (result == 0) {
			throw new Exception();
		}
		List<Project> lists = getAvailableProjects(connect, projectName);
		if (lists.isEmpty()) {
			throw new Exception("Active project with name " + projectName
					+ " is not exists in db.");
		} else if (lists.size() > 1) {
			throw new Exception("More than one active project " + projectName);
		}

		return lists.get(0);
	}

	public synchronized Flow createNewFlow(Connection connect,int version, String flowName,Project project) throws Exception {
		QueryRunner runner = datasource.getRunner();
		if (!getAvailableFlows(connect,flowName).isEmpty()) {
			throw new Exception("Active Flow with name " + flowName
					+ " already exists in db.");
		}
		final String INSERT_FLOW = "INSERT INTO project_flows (project_id, version, flow_id, modified_time) values (?,?,?,?)";
		int result = runner.update(connect, INSERT_FLOW, project.getId(),version,flowName);
		connect.commit();
		if(result==0){
			throw new Exception();
		}
		List<Flow> lists = getAvailableFlows(connect,flowName);
		if (lists.isEmpty()) {
			throw new Exception("Active project with name " + flowName
					+ " is not exists in db.");
		} else if (lists.size() > 1) {
			throw new Exception("More than one active project " + flowName);
		}
		return lists.get(0);
	}
	
	public Node createNewNode(Connection connect,Project project ,Flow flow ,String nodename,int vesrion){
		QueryRunner runner = datasource.getRunner();
		
	}

	private List<Project> getAvailableProjects(Connection connect,
			String projectName) {
		List<Project> proListsList = Lists.newArrayList();
		QueryRunner runner = datasource.getRunner();
		ProjectResultHandler handler = new ProjectResultHandler();
		try {
			proListsList = runner.query(connect,
					ProjectResultHandler.SELECT_ACTIVE_PROJECT_BY_NAME,
					handler, projectName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proListsList;
	}

	private List<Flow> getAvailableFlows(Connection connect, String flowname)
			throws Exception {
		List<Flow> flowLists = Lists.newArrayList();
		QueryRunner runner = datasource.getRunner();
		FlowResultHandler handler = new FlowResultHandler();
		flowLists = runner.query(connect,
				FlowResultHandler.SELECT_ACTION_FLOW_BY_PROJECT, handler,
				flowname);
		return flowLists;
	}
	
	private List<Node> getAvailableNodes(Connection connect, String nodename){
		List<Node> nodeLists = Lists.newArrayList();
		QueryRunner runner = datasource.getRunner();
		NodeResultHandler handler = new NodeResultHandler();
	}

	private static class ProjectResultHandler implements
			ResultSetHandler<List<Project>> {

		private static final String SELECT_PROJECT_BY_ID = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE id=?";

		private static final String SELECT_ALL_ACTIVE_PROJECTS = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE active=true";

		private static final String SELECT_ACTIVE_PROJECT_BY_NAME = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE name=? AND active=true";

		public List<Project> handle(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			List<Project> lists = Lists.newArrayList();
			Project project;
			do {
				final int id = rs.getInt(1);
				final String projectName = rs.getString(2);
				final boolean active = rs.getBoolean(3);
				final long modifiedTime = rs.getLong(4);
				final long createTime = rs.getLong(5);
				final int version = rs.getInt(6);
				final String lastModeifiedUser = rs.getString(7);
				project = new Project(id, projectName);
				project.setActive(active);
				project.setVersion(version);
				project.setCreateTimestamp(createTime);
				project.setLastModifiedTimestamp(modifiedTime);
				project.setLastModeifiedUser(lastModeifiedUser);
				lists.add(project);
			} while (rs.next());
			return lists;
		}
	}

	private class FlowResultHandler implements ResultSetHandler<List<Flow>> {
		private static final String SELECT_ACTION_FLOW_BY_PROJECT = "SELECT project_id , version ,flow_name FROM project_flows WHERE  project_id=?";

		public List<Flow> handle(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			Flow flow;
			List<Flow> lists = Lists.newArrayList();
			do {
				final int project_id = rs.getInt(1);
				final int version = rs.getInt(2);
				final String flow_name = rs.getString(3);
				flow = new Flow(flow_name);
				flow.setProject_id(project_id);
				flow.setVersion(version);
				lists.add(flow);
			} while (rs.next());
			return lists;
		}
	}
	
	private class NodeResultHandler implements ResultSetHandler<List<Node>>{

		private final String SELECT_ACTION_NODE_BY_PROJECT_FLOW="";
		public List<Node> handle(ResultSet rs) throws SQLException {
			Node node ;
			List<Node> lists = Lists.newArrayList();
			do{
				final int project_id = rs.getInt(1);
				final int version = rs.getInt(2);
				final String flow_name = rs.getString(3);
				final String node_name = rs.getString(4);
				final byte[] dataBytes = rs.getBytes(5);
				try {
					String propertyString = new String(dataBytes,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Props props = 
				Map<String, Object> maps = 
				
			}while(rs.next());
			return null;
		}
		
	}
}
