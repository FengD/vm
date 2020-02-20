package hirain.itd.hmi.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hirain.itd.hmi.demo.bean.City;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.service.ICityService;
import hirain.itd.hmi.demo.utils.Response;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("city")
public class CityController {
	@Autowired
	private ICityService cityService;

	@PostMapping("")
	@ApiOperation(value = "insert city", notes = "insert city")
	public Object insert(City city) {
		Response response;
		try {
			cityService.insert(city.getName());
			response = new Response("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response("fail", HttpStatus.FOUND);
		}
		return response.getResponse();
	}

	@PutMapping("")
	@ApiOperation(value = "update city", notes = "update city")
	public Object update(City city) {
		Response response;
		try {
			cityService.updateCityById(city.getCity_id(), city.getName());
			response = new Response("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response("fail", HttpStatus.FOUND);
		}
		return response.getResponse();
	}

	@DeleteMapping("{cityId}")
	@ApiOperation(value = "delete city by id", notes = "delete city by id")
	public Object delete(@PathVariable int cityId) {
		Response response;
		try {
			cityService.deleteCityById(cityId);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@GetMapping("")
	@ApiOperation(value = "get all city by paging", notes = "get all city by paging")
	public Object getAllCity(@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "pageCode", required = false, defaultValue = "1") int pageCode,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		Response response;
		PageBean pageBean;
		System.out.println(name + pageCode + pageSize);
		try {
			System.out.println(name + pageCode + pageSize);
			pageBean = cityService.selectAllByPage(name, pageCode, pageSize);
			response = new Response(pageBean, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@GetMapping("ifExistCityName")
	@ApiOperation(value = "check if exist city name", notes = "0:not exist  1:existed")
	public Object ifExistCityName(@RequestParam String name) {
		Response response;
		int result;
		try {
			result = cityService.ifExistCityName(name);
			response = new Response(result, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}
}
