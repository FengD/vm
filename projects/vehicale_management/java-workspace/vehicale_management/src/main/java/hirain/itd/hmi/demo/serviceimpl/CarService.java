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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Car selectByPrimaryKey(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(Car car) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(int id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

}
