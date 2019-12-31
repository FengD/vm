package hirain.itd.hmi.demo.config.handler;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.auth0.jwt.JWT;

@Component
public class CarHandshakeHandler extends DefaultHandshakeHandler {
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
			HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();

			String token = httpRequest.getParameter("token");
			if (StringUtils.isEmpty(token)) {
				return null;
			}
			final String carId = JWT.decode(token).getAudience().get(0);
			return new Principal() {
				@Override
				public String getName() {
					return carId;
				}
			};
		}
		return null;
	}
}
