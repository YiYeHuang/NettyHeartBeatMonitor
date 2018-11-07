package monitorservice.server.servicemonitor.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisRestController {

	@RequestMapping(value = "/monitor/{key}", method = RequestMethod.GET, produces = "application/json")
	public String getSingleValue(@PathVariable("key") String key){
		return "{\"service\" :\"Service 1\",\"ip\" :\"10.137.232.169\",\"port\" :\"8091\",\"lastTime\" :\"Wed Nov 07 " +
				"14:21:03 PST 2018\",\"memory\" :{\"total\" :\"17179869184\",\"free\" :\"508510208\",\"used\" :\"10323533824\"}," +
				"\"cpu\" :{\"system\" :\"0.0\",\"processed\" :\"0.0\",\"time\" :\"1358461000\"}}";
	}
}
