package message;

import core.ServiceHeartBeatModule;

import java.util.Date;

public class HeartBeat implements IMessage {
	private String ip;
	private int port;
	private Date lasttime;

	private String totalMem;
	private String freeMem;
	private String usedMem;

	private String sysCPU;
	private String processedCPU;
	private String processCPUTime;

	private String content;

	public HeartBeat() {
	}

	public HeartBeat(String content) {
		this.content = content;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProcessCPUTime(String processCPUTime) {
		this.processCPUTime = processCPUTime;
	}

	public void setProcessedCPU(String processedCPU) {
		this.processedCPU = processedCPU;
	}

	public void setTotalMem(String totalMem) {
		this.totalMem = totalMem;
	}

	public void setFreeMem(String freeMem) {
		this.freeMem = freeMem;
	}

	public void setUsedMem(String usedMem) {
		this.usedMem = usedMem;
	}

	public void setSysCPU(String sysCPU) {
		this.sysCPU = sysCPU;
	}

	public String getServiceName() {
		return ServiceHeartBeatModule.SERVICENAME;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	@Override
	public String toString() {
		 content =
		 "{\"service\" :" + '"' + ServiceHeartBeatModule.SERVICENAME + '"' + ","
		+ "\"ip\" :" + '"' + ip + '"' + ","
		+ "\"port\" :" + '"' + port + '"' + ","
		+ "\"lastTime\" :" + '"' + lasttime + '"' + ","
		+ "\"memory\" :{"
		+ "\"total\" :" + '"' + totalMem + '"' + ","
	    + "\"free\" :" + '"' + freeMem + '"' + ","
		+ "\"used\" :" + '"' + usedMem + '"' + "},"
		+ "\"cpu\" :{"
		+ "\"system\" :" + '"' + sysCPU + '"' + ","
		+ "\"processed\" :" + '"' + processedCPU + '"' + ","
		+ "\"time\" :" + '"' + processCPUTime + '"' + "}}";
		return content;
	}

	@Override
	public char getType() {
		return MessageType.HEART_BEAT;
	}

	@Override
	public String getContent() {
		if (null == content) {
			toString();
		}
		return toString();
	}

	public static void main(String[] args) {
		HeartBeat hb = new HeartBeat();
		hb.setIp("192.168.1.2");
		hb.setPort(1121);
		hb.setTotalMem("4096");
		hb.setUsedMem("1024");
		hb.setFreeMem("2000");
		hb.setSysCPU("100%");
		hb.setProcessCPUTime("1234");
		hb.setProcessedCPU("3%");
		System.out.print(hb);
	}
}
