package hirain.itd.hmi.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import hirain.itd.hmi.demo.bean.Car;

@Mapper
public interface CarMapper {
	int insert(@Param("name") String name, @Param("pwd") String pwd);

	Car selectCarById(int id);

	int updateCarById(@Param("name") String name, @Param("pwd") String pwd, @Param("id") int id);

	int deleteCarById(int id);

	List<Car> selectAll();

	Car selectCarByName(String name);

}
