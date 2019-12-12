package hirain.itd.hmi.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.Car;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Used for route Action")
@RestController
public class RouteController {
	@ApiOperation(value = "route", notes = "get all route of one car")
	@PostMapping("/route/all")
	@CarLoginToken
	public Object getAllRouteListOfOneCar(@RequestBody Car car) throws Exception {
		JSONObject jsonObject = new JSONObject();

		return jsonObject;
	}

	@ApiOperation(value = "one route info", notes = "get all the information of one route.")
	@PostMapping("/route/select")
	@CarLoginToken
	public Object getOneRouteInfo() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", "LOGOUT");
		jsonObject.put("status", "SUCCESS");
		return jsonObject;
	}
}
