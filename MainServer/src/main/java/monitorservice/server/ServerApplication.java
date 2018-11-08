package monitorservice.server;

import monitorservice.server.servicemonitor.nettyserver.MonitorServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws InterruptedException {
		MonitorServer monitorServer = MonitorServer.getInstance();
		monitorServer.start();
		SpringApplication.run(ServerApplication.class, args);
	}
}
