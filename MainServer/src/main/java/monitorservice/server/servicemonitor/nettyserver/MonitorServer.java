package monitorservice.server.servicemonitor.nettyserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import monitorservice.server.servicemonitor.codec.ServerDecoder;
import monitorservice.server.servicemonitor.codec.ServerEncoder;

public class MonitorServer {

	EventLoopGroup main;
	EventLoopGroup worker;
	ServerBootstrap boot;
	ChannelFuture cFuture;

	private static MonitorServer instance;

	private MonitorServer() {
	}

	public static synchronized MonitorServer getInstance() {
		if (null == instance) {
			instance = new MonitorServer();
		}

		return instance;
	}

	public void start() throws InterruptedException {
		main = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		int port = 8867;
		try {
			boot = new ServerBootstrap();
			boot.group(main, worker)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ch.pipeline()
									.addLast(new ServerDecoder())
									.addLast(new ServerEncoder())
									.addLast(new MonitorHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			cFuture = boot.bind(port).sync();

			System.out.println("starting server....");
		} catch (InterruptedException e) {
			e.printStackTrace();
			cFuture.channel().closeFuture().sync();
			main.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	public void stop() throws InterruptedException {
		if (null != main && null != worker) {
			cFuture.channel().closeFuture().sync();
			System.out.println("stop server....");
			main.shutdownGracefully();
			worker.shutdownGracefully();
			System.out.println("exit server....");
		}
	}
}
