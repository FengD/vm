package hirain.itd.hmi.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import hirain.itd.hmi.demo.bean.CarStatus;

@Mapper
public interface CarStatusMapper {
	CarStatus selectStatusByCarId(int carId);

	void updateStatusByCarId(@Param("carId") int carId, @Param("isLogin") boolean isLogin);

}
