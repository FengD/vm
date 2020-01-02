package hirain.itd.hmi.demo.config.handler;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import hirain.itd.hmi.demo.serviceimpl.WebSocketService;

@Component
public class CarWebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {
	private static Logger logger = LoggerFactory.getLogger(CarWebSocketDecoratorFactory.class);

	@Override
	public WebSocketHandler decorate(WebSocketHandler handler) {
		return new WebSocketHandlerDecorator(handler) {
			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				logger.debug("sessionId = {} join", session.getId());
				Principal principal = session.getPrincipal();
				if (principal != null) {
					logger.debug("key = {} save", principal.getName());
					WebSocketService.add(Integer.parseInt(principal.getName()), session);
				}
				super.afterConnectionEstablished(session);
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				logger.debug("sessionId = {} leave", session.getId());
				Principal principal = session.getPrincipal();
				if (principal != null) {
					WebSocketService.remove(Integer.parseInt(principal.getName()));
				}
				super.afterConnectionClosed(session, closeStatus);
			}
		};
	}
}
