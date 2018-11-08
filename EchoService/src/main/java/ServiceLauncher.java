import core.ServiceHeartBeatModule;

import java.io.UnsupportedEncodingException;

public class ServiceLauncher {

	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
		ServiceHeartBeatModule s1 = new ServiceHeartBeatModule("127.0.0.1", 8867, "Service1", 8091);
		s1.connect();

		ServiceHeartBeatModule s2 = new ServiceHeartBeatModule("127.0.0.1", 8867, "Service2", 8092);
		s2.connect();

		ServiceHeartBeatModule s3 = new ServiceHeartBeatModule("127.0.0.1", 8867, "Service3", 8093);
		s3.connect();
	}

}
