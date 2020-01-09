package hirain.itd.hmi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.service.ICarStatusService;
import hirain.itd.hmi.demo.serviceimpl.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "used for authentication")
@RestController
public class LoginController {
	@Autowired
	ICarService carService;
	@Autowired
	TokenService tokenService;
	@Autowired
	ICarStatusService carStatusService;

	@ApiOperation(value = "login", notes = "login parameter is name and password")
	@PostMapping("/car/login")
	public Object login(@RequestBody Car car) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Car carForBase = carService.selectCarByName(car.getName());

		if (!carForBase.getPwd().equals(car.getPwd())) {
			carStatusService.updateStatusByCarId(carForBase.getCar_id(), false);
			jsonObject.put("message", "password error, login failed.");
			jsonObject.put("action", "LOGIN");
			jsonObject.put("status", "FATAL");
			return jsonObject;
		} else {
			carStatusService.updateStatusByCarId(carForBase.getCar_id(), true);
			String token = tokenService.getToken(carForBase);
			jsonObject.put("token", token);
			jsonObject.put("action", "LOGIN");
			jsonObject.put("status", 200);
			jsonObject.put("authority", "CAR");
			jsonObject.put("car", carForBase);
			return jsonObject;
		}
	}

	@ApiOperation(value = "logout", notes = "logout parameter is name and password")
	@PostMapping("/car/logout")
	@CarLoginToken
	public Object logout(@RequestBody Car car) throws Exception {
		carStatusService.updateStatusByCarId(car.getCar_id(), false);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "LOGOUT");
		jsonObject.put("status", 200);
		return jsonObject;
	}
}
