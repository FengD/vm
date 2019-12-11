package hirain.itd.hmi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.annotation.UserLoginToken;
import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.serviceimpl.CarService;
import hirain.itd.hmi.demo.serviceimpl.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "used for authentication")
@RestController
public class LoginController {
	@Autowired
	CarService carService;
	@Autowired
	TokenService tokenService;

	@ApiOperation(value = "login", notes = "login parameter is name and password")
	@PostMapping("/login")
	public Object login(@RequestBody Car car) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Car carForBase = carService.selectCarByName(car.getName());

		if (!carForBase.getPwd().equals(car.getPwd())) {
			jsonObject.put("message", "password error, login failed.");
			jsonObject.put("status", "FATAL");
			return jsonObject;
		} else {
			String token = tokenService.getToken(carForBase);
			jsonObject.put("token", token);
			jsonObject.put("status", "SUCCESS");
			jsonObject.put("authority", carForBase.getName());
			jsonObject.put("car", carForBase);
			return jsonObject;
		}
	}

	@UserLoginToken
	@GetMapping("/testToken")
	public String getMessage() {
		return "pass";
	}
}
