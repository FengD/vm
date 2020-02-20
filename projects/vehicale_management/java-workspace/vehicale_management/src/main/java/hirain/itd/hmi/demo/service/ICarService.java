package hirain.itd.hmi.demo.service;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.bean.vo.CarProfile;
import hirain.itd.hmi.demo.bean.vo.PageBean;

public interface ICarService {
	int insert(Car car);

	CarProfile selectCarProfileById(int id) throws Exception;

	int updateCarById(Car car) throws Exception;

	int updateCarPhotoPathById(int car_id,String photo_path) throws Exception;

	int deleteCarById(int id) throws Exception;

	PageBean selectAllByPage(String type,String cityName,String projectName,int pageCode, int pageSize) throws Exception;

	Car selectCarByName(String name) throws Exception;
}
