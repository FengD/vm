package hirain.itd.hmi.demo.service;

import java.util.List;

import hirain.itd.hmi.demo.bean.Car;

public interface ICarService {
	int insert(Car car);

	Car selectByPrimaryKey(int id) throws Exception;

	int updateByPrimaryKey(Car car) throws Exception;

	int deleteByPrimaryKey(int id) throws Exception;

	List<Car> selectAll() throws Exception;

	Car selectCarByName(String name) throws Exception;

	List<Car> selectCarsByProjectName(String projectName) throws Exception;
}
