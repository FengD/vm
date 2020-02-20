package hirain.itd.hmi.demo.controller;

import hirain.itd.hmi.demo.bean.vo.CarProfile;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.service.IFileService;
import hirain.itd.hmi.demo.serviceimpl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import hirain.itd.hmi.demo.bean.Car;

import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.utils.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("car")
public class CarController {
	@Autowired
	private ICarService carService;

	@Autowired
	private RedisService redisService;

    @Autowired
	private IFileService fileService;

	@GetMapping("{carId}")
	@ApiOperation(value = "car", notes = "get car by id")
	public Object getCarById(@PathVariable int carId) {
		Response response;
		CarProfile car;
		try {
			car = carService.selectCarProfileById(carId);
			response = new Response(car, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);

		}
		return response.getResponse();
	}

	@PostMapping("")
	@ApiOperation(value = "car", notes = "insert car")
	public Object insert( Car car,@RequestParam("car-image-file") MultipartFile file) {
		Response response;
		if (file.isEmpty()) {
			return new Response("文件不能为空",HttpStatus.FOUND);
		}
		String photo_path;
		try {
			photo_path=fileService.upload(file);
			car.setPhoto_path(photo_path);
			carService.insert(car);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(null, HttpStatus.FOUND);
		}
		return response.getResponse();
	}

	@PutMapping("")
	@ApiOperation(value = "update car by id ignore car_photo_path", notes = "update car by id ignore car_photo_path")
	public Object update( @RequestBody Car car) {
		Response response;
		try {
			carService.updateCarById(car);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(null, HttpStatus.NOT_FOUND);

		}
		return response.getResponse();
	}

	@PutMapping("updatePhoto")
	@ApiOperation(value = "update car_photo_path by car_id", notes="update car_photo_path by car_id")
	public Object updatePhoto(int car_id,@RequestParam("car-image-file") MultipartFile file){
		Response response;
		if (file.isEmpty()) {
			return new Response("文件不能为空",HttpStatus.FOUND);
		}
		String photo_path;
		try {
			photo_path=fileService.upload(file);
			carService.updateCarPhotoPathById(car_id,photo_path);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(null, HttpStatus.FOUND);
		}
		return response.getResponse();

	}

	@DeleteMapping("{carId}")
	@ApiOperation(value = "car", notes = "delete car by id")
	public Object delete(@PathVariable int carId) {
		Response response;
		try {
			carService.deleteCarById(carId);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@GetMapping("")
	@ApiOperation(value = "car", notes = "get all car by paging")
	public Object getAllCar(@RequestParam(value = "type", required = false,defaultValue="") String type,
								@RequestParam(value = "cityName", required = false,defaultValue="") String cityName,
								@RequestParam(value = "projectName", required = false,defaultValue="") String projectName,
								@RequestParam(value = "pageCode", required = false, defaultValue = "1") int pageCode,
							@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		Response response;
		PageBean pageBean;
		try {
			pageBean = carService.selectAllByPage(type,cityName,projectName,pageCode, pageSize);
			response = new Response(pageBean, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}


	@GetMapping("{carId}/status")
	@ApiOperation(value = "car status", notes = "get status by car id")
	public Object getStatusByCarId(@PathVariable int carId) {
		Response response;
		try {
			response = new Response(redisService.exists(carId+""), HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);

		}
		return response.getResponse();
	}

}
