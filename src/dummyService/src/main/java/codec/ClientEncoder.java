package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import message.IMessage;

import java.nio.charset.Charset;

public class ClientEncoder extends MessageToByteEncoder<IMessage> {

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, IMessage myMessage, ByteBuf byteBuf) {
		char messageType = myMessage.getType();
		int length = myMessage.getContentLength();
		String content = myMessage.getContent();

		byteBuf.writeChar(messageType);
		byteBuf.writeInt(length);
		byteBuf.writeBytes(content.getBytes(Charset.forName("UTF-8")));
	}
}