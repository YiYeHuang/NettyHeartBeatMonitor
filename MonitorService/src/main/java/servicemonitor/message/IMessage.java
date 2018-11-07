package servicemonitor.message;

public interface IMessage {
	char getType();
	String getServiceName();
	String getContent();
}
