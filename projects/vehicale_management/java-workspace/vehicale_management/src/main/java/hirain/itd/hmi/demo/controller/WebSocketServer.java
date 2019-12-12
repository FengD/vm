package hirain.itd.hmi.demo.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/ws/itd_hmi/vehicle_management")
@Component
public class WebSocketServer {
	@PostConstruct
	public void init() {
		System.out.println("websocket loading...");
	}

	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	private static final AtomicInteger OnlineCount = new AtomicInteger(0);
	// save the session for each client
	private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

	@OnOpen
	public void onOpen(Session session) {
		SessionSet.add(session);
//		thread increment
		int cnt = OnlineCount.incrementAndGet();
		log.info("Connection established. Current connections: {}", cnt);
		SendMessage(session, "connect success");
	}

	@OnClose
	public void onClose(Session session) {
		SessionSet.remove(session);
		int cnt = OnlineCount.decrementAndGet();
		log.info("Connection cutted. Current connections: {}", cnt);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("来自客户端的消息：{}", message);
		SendMessage(session, "收到消息，消息内容：" + message);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("Error accured：{}，Session ID： {}", error.getMessage(), session.getId());
		error.printStackTrace();
	}

	public static void SendMessage(Session session, String message) {
		try {
			session.getBasicRemote()
					.sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
		} catch (IOException e) {
			log.error("Send message error：{}", e.getMessage());
			e.printStackTrace();
		}
	}

	public static void BroadCastInfo(String message) throws IOException {
		for (Session session : SessionSet) {
			if (session.isOpen()) {
				SendMessage(session, message);
			}
		}
	}

	public static void SendMessage(String sessionId, String message) throws IOException {
		Session session = null;
		for (Session s : SessionSet) {
			if (s.getId().equals(sessionId)) {
				session = s;
				break;
			}
		}
		if (session != null) {
			SendMessage(session, message);
		} else {
			log.warn("Session id not found{}", sessionId);
		}
	}

}