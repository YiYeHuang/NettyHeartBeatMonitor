package monitorservice.server.servicemonitor.api;

public interface MonitorAccess {

	String getAllService();
	String getServiceInfo(String serviceName);
}
