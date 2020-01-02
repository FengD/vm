package hirain.itd.hmi.demo.serviceimpl;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketService {
	private static Logger logger = LoggerFactory.getLogger(WebSocketService.class);
	private static ConcurrentHashMap<Integer, WebSocketSession> manager = new ConcurrentHashMap<Integer, WebSocketSession>();

	public static void add(int key, WebSocketSession webSocketSession) {
		logger.debug("add webSocket connection {} ", key);
		manager.put(key, webSocketSession);
	}

	public static void remove(int key) {
		logger.debug("remove webSocket connnection {} ", key);
		manager.remove(key);
	}

	public static WebSocketSession get(int key) {
		logger.debug("get webSocket connection {}", key);
		return manager.get(key);
	}

}
