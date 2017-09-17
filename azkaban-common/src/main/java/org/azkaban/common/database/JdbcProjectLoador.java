/**
 * JdbcProjectLoador.java
 * zmm
 * 20172017年9月14日下午10:04:31
 */
package org.azkaban.common.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;
import org.azkaban.common.executor.ExecutorLoader;
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

		int i = runner.update(INSERT_PROJECT, projectName, true,
				System.currentTimeMillis(), System.currentTimeMillis(), null,
				System.currentTimeMillis());
		connect.commit();
		if (i == 0) {
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

	private static class ProjectResultHandler implements
			ResultSetHandler<List<Project>> {

		private static String SELECT_PROJECT_BY_ID = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE id=?";

		private static String SELECT_ALL_ACTIVE_PROJECTS = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE active=true";

		private static String SELECT_ACTIVE_PROJECT_BY_NAME = "SELECT id, name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob FROM projects WHERE name=? AND active=true";

		public List<Project> handle(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			List<Project> lists = Lists.newArrayList();
			Project project;
			do{
				int id = rs.getInt(1);
				String projectName = rs.getString(2);
				boolean active = rs.getBoolean(3);
				long modifiedTime = rs.getLong(4);
				long createTime = rs.getLong(5);
				int version = rs.getInt(6);
				String lastModeifiedUser = rs.getString(7);
				project = new Project(id,projectName);
				project.setActive(active);
				project.setVersion(version);
				project.setCreateTimestamp(createTime);
				project.setLastModifiedTimestamp(modifiedTime);
				project.setLastModeifiedUser(lastModeifiedUser);
				lists.add(project);
			}while(rs.next());
			return lists;
		}
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

}
