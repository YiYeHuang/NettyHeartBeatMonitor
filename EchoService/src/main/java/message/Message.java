package message;

public class Message implements IMessage {

	private char type;
	private String serviceName;
	private String content;

	public Message(char type, String serviceName, String content) {
		this.type = type;
		this.serviceName = serviceName;
		this.content = content;
	}

	@Override
	public char getType() {
		return type;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public String getContent() {
		return content;
	}
}
