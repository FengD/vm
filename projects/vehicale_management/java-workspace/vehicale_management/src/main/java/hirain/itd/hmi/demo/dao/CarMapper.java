package hirain.itd.hmi.demo.dao;

import java.util.List;

import hirain.itd.hmi.demo.bean.vo.CarProfile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import hirain.itd.hmi.demo.bean.Car;

@Mapper
public interface CarMapper {
	int insert(Car record);

	CarProfile selectCarProfileById(int car_id);

	int updateCarById(Car car);

	int updateCarPhotoPathById(@Param("car_id") int id,@Param("photo_path") String path);

	int deleteCarById(int id);

	List<CarProfile> selectAll(@Param("type") String type,@Param("cityName") String cityName,@Param("projectName") String projectName);

	Car selectCarByName(String name);

	int deleteCarByProjectId(int priject_id);

	int deleteCarByCityId(int city_id);

}
