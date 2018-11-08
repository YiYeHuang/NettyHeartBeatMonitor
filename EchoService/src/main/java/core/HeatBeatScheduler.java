package core;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import message.Authenticate;
import message.IMessage;
import message.MessageType;
import task.HeartBeatTask;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeatBeatScheduler extends ChannelHandlerAdapter {

    private String ip;
    private int port;
    private String serviceName;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private static final String SUCCESS = "200";

    public HeatBeatScheduler(int port, String serviceName) {
        this.port = port;
        this.serviceName = serviceName;

        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new Authenticate(ip, port, serviceName));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof IMessage){

            if (((IMessage) msg).getType() == MessageType.AUTH) {
                if(SUCCESS.equals(((IMessage) msg).getContent())) {

                    // Launching Scheduled heart Beat.
                    this.scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(
                            new HeartBeatTask(ctx,ip,port, serviceName),2,3,
                            TimeUnit.SECONDS);

                } else {
                    System.out.println("Server message: " + msg);
                }
            } else if (((IMessage) msg).getType() == MessageType.HEART_BEAT) {
                System.out.println("Server message: " + ((IMessage) msg).getContent());
            }

        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();

        if(this.scheduledFuture != null){
            this.scheduledFuture.cancel(true);
            this.scheduledFuture = null;
        }
    }
}
