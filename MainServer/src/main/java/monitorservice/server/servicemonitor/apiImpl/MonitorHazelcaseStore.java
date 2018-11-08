package monitorservice.server.servicemonitor.apiImpl;

import monitorservice.server.servicemonitor.api.MonitorAccess;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;

public class MonitorHazelcaseStore implements MonitorAccess {

	private final String MONITOR_MAPSTORE = "MonitorMap";
	private final String SERVICE_MAPSTORE = "Services";

	private static MonitorHazelcaseStore instance;
	private HazelcastInstance hzInstance;

	private IList<Object> serviceList;
	private IMap<Object, Object> monitorMap;

	private MonitorHazelcaseStore() {
		Config hzConfig;
		this.hzInstance = Hazelcast.newHazelcastInstance();
		this.monitorMap = hzInstance.getMap(MONITOR_MAPSTORE);
		this.serviceList = hzInstance.getList(SERVICE_MAPSTORE);
	}

	public static synchronized MonitorHazelcaseStore getInstance() {
		if (null == instance) {
			instance = new MonitorHazelcaseStore();
		}

		return instance;
	}

	public void logMonitorInfo(String key, String value) {
		monitorMap.put(key, value);
	}

	public void logServices(String serviceName) {
		serviceList.add(serviceName);
	}

	@Override
	public String getAllService() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"Services\":[");
		for (int i = 0; i < serviceList.size(); i++) {
			sb.append('"').append(serviceList.get(i)).append('"');
			if (i != serviceList.size() - 1) {
				sb.append(',');
			}
		}
		sb.append("]}");

		return sb.toString();
	}

	@Override
	public String getServiceInfo(String serviceName) {
		return (String) monitorMap.get(serviceName);
	}
}
