package hirain.itd.hmi.demo.config.handler;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import hirain.itd.hmi.demo.bean.vo.CarProfile;
import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.serviceimpl.WebSocketService;

@Component
public class CarWebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {
	private static Logger logger = LoggerFactory.getLogger(CarWebSocketDecoratorFactory.class);

	@Autowired
	private ICarService carService;

	@Override
	public WebSocketHandler decorate(WebSocketHandler handler) {
		return new WebSocketHandlerDecorator(handler) {
			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				logger.debug("sessionId = {} join", session.getId());
				Principal principal = session.getPrincipal();
				if (principal != null) {
					CarProfile carProfile = carService.selectCarProfileById(Integer.parseInt(principal.getName()));
					logger.info("key = {} save", carProfile.getType());
					WebSocketService.add(carProfile.getType(), session);
				}
				super.afterConnectionEstablished(session);
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				logger.debug("sessionId = {} leave", session.getId());
				Principal principal = session.getPrincipal();
				if (principal != null) {
					CarProfile carProfile = carService.selectCarProfileById(Integer.parseInt(principal.getName()));
					logger.info("key = {} delete", carProfile.getType());
					WebSocketService.remove(carProfile.getType());
				}
				super.afterConnectionClosed(session, closeStatus);
			}
		};
	}
}
