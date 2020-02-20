package hirain.itd.hmi.demo.serviceimpl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import hirain.itd.hmi.demo.bean.Car;

@Service
public class TokenService {

	public String getToken(Car car) {
		Date start = new Date();
		long currentTime = System.currentTimeMillis();
		long oneDayLater = currentTime + 24 * 60 * 60*1000 ;
		Date end = new Date(oneDayLater);
		String token = JWT.create().withAudience("" + car.getCar_id()).withIssuedAt(start).withExpiresAt(end)
				.sign(Algorithm.HMAC256(car.getPwd()));
		return token;
	}
}