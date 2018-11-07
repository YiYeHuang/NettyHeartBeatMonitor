package message;

public class Error implements IMessage {

	private final String message;

	public Error(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public char getType() {
		return MessageType.ERROR;
	}

	@Override
	public String getServiceName() {
		return "ERROR";
	}

	@Override
	public String getContent() {
		return message;
	}
}
