package hirain.itd.hmi.demo.serviceimpl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketService {
	private static Logger logger = LoggerFactory.getLogger(WebSocketService.class);
	private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<String, WebSocketSession>();

	public static void add(String key, WebSocketSession webSocketSession) {
		logger.info("add webSocket connection {} ", key);
		manager.put(key, webSocketSession);
	}

	public static void remove(String key) {
		logger.info("remove webSocket connnection {} ", key);
		manager.remove(key);
		TcpWebSocketPairService.remove(key);
	}

	public static WebSocketSession get(String key) {
		logger.debug("get webSocket connection {}", key);
		return manager.get(key);
	}

}
