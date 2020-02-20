//package hirain.itd.hmi.demo.useless;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioDatagramChannel;
//
////@Component
//public class UdpServer {
//	private EventLoopGroup group = new NioEventLoopGroup();
//	private static Logger logger = LoggerFactory.getLogger(UdpServer.class);
//
//	@Value("${netty.udp.port}")
//	private Integer port;
//
//	@PostConstruct
//	public void start() throws InterruptedException {
//		try {
//			Bootstrap bootstrap = new Bootstrap();
//			EventLoopGroup group = new NioEventLoopGroup();
//			bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
//					.handler(new UdpServerHandler());
//
//			bootstrap.bind(port).sync();
//			logger.info("udp server start at port {}", port);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			group.shutdownGracefully().sync();
//		}
//	}
//
//	@PreDestroy
//	public void destory() throws InterruptedException {
//		group.shutdownGracefully().sync();
//		logger.info("close udp server");
//	}
//}