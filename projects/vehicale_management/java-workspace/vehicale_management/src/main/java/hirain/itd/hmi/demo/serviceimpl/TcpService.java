package hirain.itd.hmi.demo.serviceimpl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.socket.SocketChannel;

public class TcpService {
	private static Logger logger = LoggerFactory.getLogger(TcpService.class);
	private static ConcurrentHashMap<String, SocketChannel> manager = new ConcurrentHashMap<String, SocketChannel>();

	public static void add(String key, SocketChannel socketChannel) {
		logger.info("add tcp connection {} ", key);
		manager.put(key, socketChannel);
	}

	public static void remove(String key) {
		logger.info("remove tcp connnection {} ", key);
		manager.remove(key);
	}

	public static SocketChannel get(String key) {
		logger.debug("get tcp connection {}", key);
		return manager.get(key);
	}

}
