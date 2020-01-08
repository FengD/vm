package hirain.itd.hmi.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.CarStatus;
import hirain.itd.hmi.demo.dao.CarStatusMapper;
import hirain.itd.hmi.demo.service.ICarStatusService;

@Service
public class CarStatusService implements ICarStatusService {
	@Autowired
	private CarStatusMapper carStatusMapper;

	@Override
	public CarStatus selectStatusByCarId(int carId) {
		return carStatusMapper.selectStatusByCarId(carId);
	}

	@Override
	public void updateStatusByCarId(int carId, boolean isLogin) {
		carStatusMapper.updateStatusByCarId(carId, isLogin);
	}
}
