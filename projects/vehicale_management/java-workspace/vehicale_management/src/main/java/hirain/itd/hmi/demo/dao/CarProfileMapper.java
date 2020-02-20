package hirain.itd.hmi.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import hirain.itd.hmi.demo.bean.vo.CarProfile;

@Mapper
public interface CarProfileMapper {
	CarProfile selectProfileByCarId(int carId);
}
