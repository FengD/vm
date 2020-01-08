package hirain.itd.hmi.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.service.ICarProfileService;
import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.service.ICarStatusService;
import hirain.itd.hmi.demo.utils.Response;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("car")
public class CarController {
	@Autowired
	private ICarService carService;

	@Autowired
	private ICarProfileService carProfileService;

	@Autowired
	private ICarStatusService carStatusService;

	@GetMapping("{carId}")
	@ApiOperation(value = "car", notes = "get car by id")
	public Object getCarById(@PathVariable int carId) {
		Response response;
		Car car;
		try {
			car = carService.selectCarById(carId);
			response = new Response(car, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);

		}
		return response.getResponse();
	}

	@PostMapping("")
	@ApiOperation(value = "car", notes = "inset car")
	public Object insert(@RequestBody Car car) {
		Response response;
		try {
			carService.insert(car.getName(), car.getPwd());
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(null, HttpStatus.FOUND);
		}
		return response.getResponse();
	}

	@PutMapping("{carId}")
	@ApiOperation(value = "car", notes = "update car by id")
	public Object update(@PathVariable int carId, @RequestBody Car car) {
		Response response;
		try {
			carService.updateCarById(carId, car.getName(), car.getPwd());
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(null, HttpStatus.NOT_FOUND);

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

	@GetMapping("all")
	@ApiOperation(value = "car", notes = "get all car")
	public Object getAllCar() {
		Response response;
		List<Car> cars;
		try {
			cars = carService.selectAll();
			response = new Response(cars, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@GetMapping("{carId}/profile")
	@ApiOperation(value = "car profile", notes = "get profile by car id")
	public Object getProfileByCarId(@PathVariable int carId) {
		Response response;
		try {
			response = new Response(carProfileService.selectProfileByCarId(carId), HttpStatus.OK);
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
			response = new Response(carStatusService.selectStatusByCarId(carId), HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);

		}
		return response.getResponse();
	}

}
