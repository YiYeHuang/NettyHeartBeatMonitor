package message;

public class Authenticate implements IMessage {

	private final String ip;

	private final int port;

	public Authenticate(String ip, int port) {
		this.ip = ip;
		this.port = port;
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
	public int getContentLength() {
		return toString().length();
	}

	@Override
	public String getContent() {
		return toString();
	}
}
