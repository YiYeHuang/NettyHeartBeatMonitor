package servicemonitor;

import servicemonitor.nettyserver.MonitorServer;

public class ServerLauncher {
	public static void main(String[] args) throws InterruptedException {
		MonitorServer server = MonitorServer.getInstance();
		server.start();
	}
}
