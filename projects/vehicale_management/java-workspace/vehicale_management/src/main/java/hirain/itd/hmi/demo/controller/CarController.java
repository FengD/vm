package hirain.itd.hmi.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.service.ICarService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private ICarService carService;
//	@Autowired
//	private HttpServletRequest myHttpRequest;
//
//	@Autowired
//	private HttpServletResponse myHttpResponse;

	@GetMapping("all")
	public List<Car> getAllCar() {
		List<Car> cars;
		try {
			cars = carService.selectAll();
			return cars;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
