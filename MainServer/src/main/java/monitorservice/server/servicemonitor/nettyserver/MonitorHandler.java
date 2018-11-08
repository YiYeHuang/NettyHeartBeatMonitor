package monitorservice.server.servicemonitor.nettyserver;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import monitorservice.server.servicemonitor.apiImpl.MonitorHazelcaseStore;
import monitorservice.server.servicemonitor.message.IMessage;
import monitorservice.server.servicemonitor.message.Message;
import monitorservice.server.servicemonitor.message.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hazelcast.core.HazelcastInstance;

public class MonitorHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        IMessage inbound = (IMessage) msg;

        if(((IMessage) msg).getType() == MessageType.AUTH){
            MonitorHazelcaseStore.getInstance().logServices(((IMessage) msg).getServiceName());
            ctx.writeAndFlush(new Message(MessageType.AUTH, ((IMessage) msg).getServiceName(), "200"));
        } else if(((IMessage) msg).getType() == MessageType.HEART_BEAT){

            System.out.println(((IMessage) msg).getContent());

            ctx.writeAndFlush("Get the Beep");

            MonitorHazelcaseStore.getInstance().logMonitorInfo(
                    ((IMessage) msg).getServiceName(),
                    ((IMessage) msg).getContent());
            ctx.writeAndFlush(new Message(MessageType.HEART_BEAT,
                    ((IMessage) msg).getServiceName(), "Heart Beat Received"));
        }
    }


}
