package hirain.itd.hmi.demo.service;

import hirain.itd.hmi.demo.bean.CarStatus;

public interface ICarStatusService {
	CarStatus selectStatusByCarId(int carId);

	void updateStatusByCarId(int carId, boolean isLogin);
}
