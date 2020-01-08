package hirain.itd.hmi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import hirain.itd.hmi.demo.config.handler.CarHandshakeHandler;
import hirain.itd.hmi.demo.config.handler.CarWebSocketDecoratorFactory;
import hirain.itd.hmi.demo.config.interceptor.CarHandShake;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	final private String endPoint = "/ws/car";
	final private String allowedOrigins = "*";

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(endPoint).addInterceptors(handeShakeInterceptor()).setAllowedOrigins(allowedOrigins)
				.setHandshakeHandler(handShakeHandler());
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue", "/topic");
		registry.setUserDestinationPrefix("/user");
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration.addDecoratorFactory(webSocketDecoratorFactory());
	}

	@Bean
	public CarWebSocketDecoratorFactory webSocketDecoratorFactory() {
		return new CarWebSocketDecoratorFactory();
	}

	@Bean
	public CarHandshakeHandler handShakeHandler() {
		return new CarHandshakeHandler();
	}

	@Bean
	public CarHandShake handeShakeInterceptor() {
		return new CarHandShake();
	}
}
