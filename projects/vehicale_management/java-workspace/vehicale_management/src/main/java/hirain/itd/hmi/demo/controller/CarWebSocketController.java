package hirain.itd.hmi.demo.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import hirain.itd.hmi.demo.serviceimpl.WebSocketService;

@RestController
public class CarWebSocketController {
	@PostMapping("/sendToCar/{carId}")
	public void sendUser(int carId) {
		WebSocketSession webSocketSession = WebSocketService.get(carId);
		if (webSocketSession != null) {
			TextMessage m = new TextMessage("sdfsdfsd");
			try {
				webSocketSession.sendMessage(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}