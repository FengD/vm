package hirain.itd.hmi.demo.serviceimpl;

import java.util.concurrent.ConcurrentHashMap;

public class TcpWebSocketPairService {
	private static ConcurrentHashMap<String, String> manager = new ConcurrentHashMap<String, String>();

	public static void add(String webSocketKey, String tcpKey) {
		manager.put(webSocketKey, tcpKey);
	}

	public static void remove(String webSocketKey) {
		manager.remove(webSocketKey);
	}

	public static String get(String webSocketKey) {
		return manager.get(webSocketKey);
	}
}
