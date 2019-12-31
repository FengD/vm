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
	public int insert(Car car) {
		return carMapper.insert(car);
	}

	@Override
	public Car selectByPrimaryKey(int id) throws Exception {
		return carMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Car car) throws Exception {
		return carMapper.updateByPrimaryKey(car);
	}

	@Override
	public int deleteByPrimaryKey(int id) throws Exception {
		return carMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Car> selectAll() throws Exception {
		return carMapper.selectAll();
	}

	@Override
	public Car selectCarByName(String name) throws Exception {
		return carMapper.selectCarByName(name);
	}

	@Override
	public List<Car> selectCarsByProjectName(String projectName) throws Exception {
		return carMapper.selectCarsByProjectName(projectName);
	}

}
