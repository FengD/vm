package hirain.itd.hmi.demo.config.interceptor;

import java.util.HashMap;
import java.util.Map;

import hirain.itd.hmi.demo.bean.vo.CarProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.serviceimpl.CarService;

@Component
public class CarHandShake implements HandshakeInterceptor {
	@Autowired
	CarService carService;

	private static Logger logger = LoggerFactory.getLogger(CarHandShake.class);

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		logger.debug("car handeshake");

		Map<String, String> paramterMap = parseParameterMap(request.getURI().getQuery());
		String token = paramterMap.get("token");

		String carId = "";
		try {
			carId = JWT.decode(token).getAudience().get(0);
		} catch (JWTDecodeException j) {
			throw new RuntimeException("401");
		}

		CarProfile car = carService.selectCarProfileById(Integer.parseInt(carId));

		if (car == null) {
			throw new RuntimeException("role not exist.");
		}

		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(car.getPwd())).build();
		try {
			jwtVerifier.verify(token);
		} catch (JWTVerificationException e) {
			throw new RuntimeException("401");
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		logger.debug("after handshark");
	}

	private Map<String, String> parseParameterMap(String queryString) {
		Map<String, String> parameterMap = new HashMap<>();
		String[] parameters = queryString.split("&");
		for (String parameter : parameters) {
			String[] paramPair = parameter.split("=");
			if (paramPair.length == 2) {
				parameterMap.put(paramPair[0], paramPair[1]);
			}
		}
		return parameterMap;
	}
}