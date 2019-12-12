package hirain.itd.hmi.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import hirain.itd.hmi.demo.annotation.PassToken;
import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.serviceimpl.CarService;

public class AuthenticationInterceptor implements HandlerInterceptor {
	@Autowired
	CarService carService;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object) throws Exception {
		// get token from http header
		String token = httpServletRequest.getHeader("token");
		System.out.println(token);
		// if not project in method pass
		if (!(object instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) object;
		java.lang.reflect.Method method = handlerMethod.getMethod();
		// check if it has PassToken
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		// check if the user need login
		if (method.isAnnotationPresent(CarLoginToken.class)) {
			CarLoginToken userLoginToken = method.getAnnotation(CarLoginToken.class);
			if (userLoginToken.required()) {
				if (token == null) {
					throw new RuntimeException("no token. relogin.");
				}

				String carId;
				try {
					carId = JWT.decode(token).getAudience().get(0);
				} catch (JWTDecodeException j) {
					throw new RuntimeException("401");
				}
				System.out.println(carId);
				Car car = carService.selectByPrimaryKey(Integer.parseInt(carId));
				System.out.println(car);
				if (car == null) {
					throw new RuntimeException("role not exist.");
				}
				// 验证 token
				JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(car.getPwd())).build();
				try {
					jwtVerifier.verify(token);
				} catch (JWTVerificationException e) {
					throw new RuntimeException("401");
				}
				return true;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {

	}
}