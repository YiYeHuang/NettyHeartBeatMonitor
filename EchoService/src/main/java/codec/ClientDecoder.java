package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import message.Authenticate;
import message.Error;
import message.HeartBeat;
import message.IMessage;
import message.MessageType;

import java.nio.charset.Charset;
import java.util.List;

public class ClientDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		char type = byteBuf.readChar();
		int length = byteBuf.readInt();
		byte[] body = new byte[length];
		byteBuf.readBytes(body);
		String content = new String(body, Charset.forName("UTF-8"));

		IMessage outMessage = null;
		if (type == MessageType.AUTH) {
			outMessage = new Authenticate(content);
		} else if (type == MessageType.HEART_BEAT) {
			outMessage = new HeartBeat(content);
		} else {
			outMessage = new Error("Invalid Request");
		}

		list.add(outMessage);
	}
}
