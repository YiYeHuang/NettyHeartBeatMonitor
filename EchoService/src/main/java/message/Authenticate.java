package message;

import core.ServiceHeartBeatModule;

public class Authenticate implements IMessage {

	private final String ip;
	private String serviceName;
	private final int port;

	public Authenticate(String ip, int port, String serviceName) {
		this.ip = ip;
		this.serviceName = serviceName;
		this.port = port;
	}

	public Authenticate(String content) {
		String[] input = content.split(":");
		if (input.length == 2) {
			this.ip = input[0];
			this.port = Integer.valueOf(input[1]);
		} else {
			ip = "127.0.0.1";
			port = 1111;
		}
	}

	@Override
	public String toString() {
		return this.ip + ":" + this.port;
	}

	@Override
	public char getType() {
		return MessageType.AUTH;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public String getContent() {
		return toString();
	}
}
