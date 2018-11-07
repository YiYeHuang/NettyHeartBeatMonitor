package servicemonitor.nettyserver;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import servicemonitor.message.HeartBeat;
import servicemonitor.message.IMessage;
import servicemonitor.message.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorHandler extends ChannelHandlerAdapter {

    private Map<String, HeartBeat> heartInfoMap = new HashMap<String, HeartBeat>();

    private static final List<String> authList = new ArrayList<String>();

    static {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        IMessage inbound = (IMessage) msg;

        if(((IMessage) msg).getType() == MessageType.AUTH){
            ctx.writeAndFlush("OK");
        } else if(((IMessage) msg).getType() == MessageType.HEART_BEAT){

            System.out.println((HeartBeat)msg);

            ctx.writeAndFlush("Get the Beep");

            HeartBeat heartInfo = (HeartBeat)msg;
            heartInfoMap.put(heartInfo.getIp() + ":" + heartInfo.getPort(),heartInfo);
        }
    }


}
