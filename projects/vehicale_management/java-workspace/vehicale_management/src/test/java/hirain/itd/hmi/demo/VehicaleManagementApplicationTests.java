package hirain.itd.hmi.demo;

//import org.junit.Assert;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

        import hirain.itd.hmi.demo.serviceimpl.CarService;

@SpringBootTest
class VehicaleManagementApplicationTests {
	@Autowired
	private CarService carService;

	@Test
	public void carInsert() throws Exception {
		/*PageBean cars = carService.selectAllByPage(1,5);
		System.out.println(cars);*/
		//Assert.assertTrue(cars.size() == 4);
//		carService.insert("hirain", "hirain");
	}

}
