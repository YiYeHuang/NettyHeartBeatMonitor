package core;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import message.Authenticate;
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
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private static final String SUCCESS = "OK";

    public HeatBeatScheduler(int port) {
        this.port = port;

        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new Authenticate(ip, port));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof String){

            if(SUCCESS.equals((String)msg)){

                // Launching Scheduled heart Beat.
                this.scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(
                        new HeartBeatTask(ctx,ip,port),2,3,
                        TimeUnit.SECONDS);

            } else {
                System.out.println("Server message: " + msg);
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
