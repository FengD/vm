package hirain.itd.hmi.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.dao.CarMapper;
import hirain.itd.hmi.demo.service.ICarService;

@Service
public class CarService implements ICarService {
	@Autowired
	private CarMapper carMapper;

	@Override
	public int insert(String name, String pwd) {
		return carMapper.insert(name, pwd);
	}

	@Override
	public Car selectCarById(int id) throws Exception {
		return carMapper.selectCarById(id);
	}

	@Override
	public int updateCarById(int id, String name, String pwd) throws Exception {
		return carMapper.updateCarById(name, pwd, id);
	}

	@Override
	public int deleteCarById(int id) throws Exception {
		return carMapper.deleteCarById(id);
	}

	@Override
	public List<Car> selectAll() throws Exception {
		return carMapper.selectAll();
	}

	@Override
	public Car selectCarByName(String name) throws Exception {
		return carMapper.selectCarByName(name);
	}

}
