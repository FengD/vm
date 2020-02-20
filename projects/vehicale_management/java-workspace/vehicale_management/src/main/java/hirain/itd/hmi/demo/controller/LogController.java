package hirain.itd.hmi.demo.controller;

import hirain.itd.hmi.demo.serviceimpl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.serviceimpl.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "used for authentication")
@RestController
public class LogController {
	@Autowired
	ICarService carService;
	@Autowired
	TokenService tokenService;
	@Autowired
	private RedisService redisService;

	@ApiOperation(value = "login", notes = "login parameter is name and password")
	@PostMapping("/car/login")
	public Object login(@RequestBody Car car) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Car carForBase = carService.selectCarByName(car.getName());

		if (!carForBase.getPwd().equals(car.getPwd())) {
			jsonObject.put("message", "password error, login failed.");
			jsonObject.put("action", "LOGIN");
			jsonObject.put("status", "FATAL");
			return jsonObject;
		} else {
			String token = tokenService.getToken(carForBase);
            if(redisService.set(carForBase.getCar_id()+"",true,(long)24 * 60 * 60 ))
			{
				jsonObject.put("token", token);
				jsonObject.put("action", "LOGIN");
				jsonObject.put("status", 200);
				jsonObject.put("authority", "CAR");
				jsonObject.put("car", carForBase);
				return jsonObject;
			}
          else {
				jsonObject.put("message", "request timeout");
				jsonObject.put("action", "LOGIN");
				jsonObject.put("status", "FATAL");
				return jsonObject;
			}
		}
	}

	@ApiOperation(value = "logout", notes = "logout parameter is name and password")
	@PostMapping("/car/{carId}/logout")
	@CarLoginToken
	public Object logout(@PathVariable int carId) throws Exception {

			redisService.removeKey(carId+"");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("action", "LOGOUT");
			jsonObject.put("status", 200);
			return jsonObject;
	}
}
