package servicemonitor.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import servicemonitor.message.Message;

import java.nio.charset.Charset;
import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		char type = byteBuf.readChar();

		int serviceNameLength = byteBuf.readInt();
		byte[] name = new byte[serviceNameLength];
		byteBuf.readBytes(name);
		String serviceName = new String(name, Charset.forName("UTF-8"));

		int contentLength = byteBuf.readInt();
		byte[] contentBuf = new byte[contentLength];
		byteBuf.readBytes(contentBuf);
		String content = new String(contentBuf, Charset.forName("UTF-8"));

		list.add(new Message(type, serviceName, content));
	}
}
