package hirain.itd.hmi.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.serviceimpl.CarService;

@SpringBootTest
class VehicaleManagementApplicationTests {
	@Autowired
	private CarService carService;

	@Test
	public void carInsert() throws Exception {
		List<Car> cars = carService.selectAll();
		System.out.println(cars.size());
		Assert.assertTrue(cars.size() == 4);
//		carService.insert("hirain", "hirain");
	}

}
