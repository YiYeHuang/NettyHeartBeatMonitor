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
	public int getContentLength() {
		return message.length();
	}

	@Override
	public String getContent() {
		return message;
	}
}
