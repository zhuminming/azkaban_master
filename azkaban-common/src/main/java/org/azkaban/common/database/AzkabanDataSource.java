package org.azkaban.common.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.azkaban.common.utils.Props;

public class AzkabanDataSource extends BasicDataSource {

	private AzkabanDataSource(String host, int port, String dbName,
			String user, String password, int numConnections) {
		String url = "jdbc:mysql://" + (host + ":" + port + "/" + dbName);
		addConnectionProperty("useUnicode", "yes");
		addConnectionProperty("characterEncoding", "UTF-8");
		setDriverClassName("com.mysql.jdbc.Driver");
		setUsername(user);
		setPassword(password);
		setUrl(url);
		setMaxActive(numConnections);

	}

	public static AzkabanDataSource getDataSource(Props props) {
		String databaseType = props.getString("database.type");
		AzkabanDataSource dataSource = null;
		if (databaseType.equals("mysql")) {
			int port = props.getInt("mysql.port");
			String host = props.getString("mysql.host");
			String database = props.getString("mysql.database");
			String user = props.getString("mysql.user");
			String password = props.getString("mysql.password");
			int numconnections = props.getInt("mysql.numconnections");
			dataSource = new AzkabanDataSource(host, port, database, user,
					password, numconnections);
		}
		return dataSource;
	}
}
