package monitorservice.server.servicemonitor.rest;

import monitorservice.server.servicemonitor.apiImpl.MonitorHazelcaseStore;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisRestController {


	@RequestMapping(value = "/service", method = RequestMethod.GET, produces = "application/json")
	public String getAllMonitor() {
		return MonitorHazelcaseStore.getInstance().getAllService();
	}

	@RequestMapping(value = "/monitor/{key}", method = RequestMethod.GET, produces = "application/json")
	public String getMonitorValue(@PathVariable("key") String key){
		return MonitorHazelcaseStore.getInstance().getServiceInfo(key);
	}
}
