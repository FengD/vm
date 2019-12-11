package hirain.itd.hmi.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hirain.itd.hmi.demo.bean.Car;

@Mapper
public interface CarMapper {
	int insert(Car car);

	Car selectByPrimaryKey(String id);

	int updateByPrimaryKey(Car car);

	int deleteByPrimaryKey(int id);

	List<Car> selectAll();

	Car selectCarByName(String name);

	List<Car> selectCarsByProjectName(String projectName);
}
