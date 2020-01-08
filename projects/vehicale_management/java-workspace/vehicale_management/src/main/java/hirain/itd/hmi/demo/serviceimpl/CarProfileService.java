package hirain.itd.hmi.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.CarProfile;
import hirain.itd.hmi.demo.dao.CarProfileMapper;
import hirain.itd.hmi.demo.service.ICarProfileService;

@Service
public class CarProfileService implements ICarProfileService {
	@Autowired
	private CarProfileMapper carProfileMapper;

	@Override
	public CarProfile selectProfileByCarId(int carId) {
		return carProfileMapper.selectProfileByCarId(carId);
	}

}
