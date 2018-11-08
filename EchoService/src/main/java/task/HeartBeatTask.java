package task;

import io.netty.channel.ChannelHandlerContext;
import message.HeartBeat;

import java.lang.management.ManagementFactory;
import java.util.Date;

import com.sun.management.OperatingSystemMXBean;

public class HeartBeatTask implements Runnable{

	private ChannelHandlerContext ctx;

	private HeartBeat heartInfo = new HeartBeat();

	public HeartBeatTask(ChannelHandlerContext ctx, String ip, int port, String serviceName) {
		this.ctx = ctx;
		this.heartInfo.setServiceName(serviceName);

		heartInfo.setIp(ip);
		heartInfo.setPort(port);
	}

	@Override
	public void run() {
		try{
			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

			heartInfo.setTotalMem(String.valueOf(osBean.getTotalPhysicalMemorySize()));
			heartInfo.setFreeMem(String.valueOf(osBean.getFreePhysicalMemorySize()));
			heartInfo.setUsedMem(String.valueOf(osBean.getCommittedVirtualMemorySize()));

			heartInfo.setSysCPU(String.valueOf(osBean.getSystemCpuLoad()));
			heartInfo.setProcessedCPU(String.valueOf(osBean.getProcessCpuLoad()));
			heartInfo.setProcessCPUTime(String.valueOf(osBean.getProcessCpuTime()));

			heartInfo.setLasttime(new Date());
			ctx.writeAndFlush(heartInfo);

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
