package hirain.itd.hmi.demo;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class TcpServer {
	private EventLoopGroup boss = new NioEventLoopGroup();
	private EventLoopGroup work = new NioEventLoopGroup();
	private static Logger logger = LoggerFactory.getLogger(TcpServer.class);

	@Value("${netty.tcp.port}")
	private Integer port;

	@PostConstruct
	public void start() throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, work).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
				.option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true).childHandler(new TcpServerChannelInitializer());
		ChannelFuture future = bootstrap.bind().sync();
		if (future.isSuccess()) {
			logger.info("tcp server start at port {}", port);
		}
	}

	@PreDestroy
	public void destory() throws InterruptedException {
		boss.shutdownGracefully().sync();
		work.shutdownGracefully().sync();
		logger.info("close tcp server");
	}
}