package servicemonitor.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import servicemonitor.message.IMessage;
import servicemonitor.message.MessageType;

import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		char type = byteBuf.readChar();
		int length = byteBuf.readInt();
		byte[] body = new byte[length];
		byteBuf.readBytes(body);

		IMessage outMessage = null;
		if (type == MessageType.AUTH) {

		} else if (type == MessageType.HEART_BEAT) {

		} else {

		}

		list.add(outMessage);
	}
}
