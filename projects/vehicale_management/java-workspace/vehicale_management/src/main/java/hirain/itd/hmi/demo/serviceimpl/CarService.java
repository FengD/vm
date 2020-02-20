package hirain.itd.hmi.demo.serviceimpl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import hirain.itd.hmi.demo.bean.vo.CarProfile;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.Car;
import hirain.itd.hmi.demo.dao.CarMapper;
import hirain.itd.hmi.demo.service.ICarService;

@Service
public class CarService implements ICarService {
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private PageService pageService;

	@Override
	public int insert(Car car) {
		return carMapper.insert(car);
	}

	@Override
	public CarProfile selectCarProfileById(int id) throws Exception {
		return carMapper.selectCarProfileById(id);
	}

	@Override
	public int updateCarById(Car car) throws Exception {
		return carMapper.updateCarById( car);
	}

	@Override
	public int updateCarPhotoPathById(int car_id,String photo_path) throws Exception{
		return carMapper.updateCarPhotoPathById(car_id,photo_path);
	}

	@Override
	public int deleteCarById(int id) throws Exception {
		return carMapper.deleteCarById(id);
	}

	@Override
	public PageBean selectAllByPage(String type,String cityName,String projectName,int pageCode, int pageSize) throws Exception {
		PageHelper.startPage(pageCode,pageSize);
		List<CarProfile> all=carMapper.selectAll(type.trim(),cityName.trim(),projectName.trim());
		Page<CarProfile> p=(Page<CarProfile>)all;
		return pageService.getBeanByPage(p,pageCode,pageSize);
	}

	@Override
	public Car selectCarByName(String name) throws Exception {
		return carMapper.selectCarByName(name);
	}

}
