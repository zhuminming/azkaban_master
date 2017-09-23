package azkaban.execapp;

import org.azkaban.common.executor.ExecutorLoader;
import org.azkaban.common.executor.JdbcExecutorLoader;
import org.azkaban.common.project.ProjectLoader;
import org.azkaban.common.utils.Props;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class AzkabanExecutorServer {
    private Server server;
    private ExecutorManager execmanager;
    private Props props;
    private ExecutorLoader executorLoader;
    private ProjectLoader projectLoader;

    public AzkabanExecutorServer(Props props) {
	this.server = new Server();
	this.executorLoader= createExecutorLoadr(props);
	execmanager = new ExecutorManager(props, executorLoader, projectLoader);
    }

    public static void main(String[] args) throws Exception {
	Server server = new Server(8081);
	Context root = new Context(server, "/", Context.SESSIONS);
	root.addServlet(new ServletHolder(new ExecutorServlet()), "/executor");
	server.start();
    }

    private ExecutorLoader createExecutorLoadr(Props props){
	return new JdbcExecutorLoader(props);
    }
    
    public ExecutorManager getExecmanager() {
        return execmanager;
    }

    public void setExecmanager(ExecutorManager execmanager) {
        this.execmanager = execmanager;
    }

}
