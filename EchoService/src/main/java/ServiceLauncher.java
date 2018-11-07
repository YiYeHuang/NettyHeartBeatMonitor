import core.ServiceHeartBeatModule;

import java.io.UnsupportedEncodingException;

public class ServiceLauncher {

	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
		ServiceHeartBeatModule s1 = new ServiceHeartBeatModule("127.0.0.1", 8867, "Service 1", 8091);
		s1.connect();
	}

}
