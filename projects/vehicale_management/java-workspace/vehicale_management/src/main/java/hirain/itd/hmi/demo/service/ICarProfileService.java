package hirain.itd.hmi.demo.service;

import hirain.itd.hmi.demo.bean.CarProfile;

public interface ICarProfileService {
	CarProfile selectProfileByCarId(int carId);
}
