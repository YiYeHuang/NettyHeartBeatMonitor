package message;

public interface IMessage {
	char getType();
	int getContentLength();
	String getContent();
}
