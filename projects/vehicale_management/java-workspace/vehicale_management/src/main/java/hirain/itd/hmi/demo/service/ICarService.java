package hirain.itd.hmi.demo.service;

import java.util.List;

import hirain.itd.hmi.demo.bean.Car;

public interface ICarService {
	int insert(String name, String pwd);

	Car selectCarById(int id) throws Exception;

	int updateCarById(int id, String name, String pwd) throws Exception;

	int deleteCarById(int id) throws Exception;

	List<Car> selectAll() throws Exception;

	Car selectCarByName(String name) throws Exception;
}
