package hirain.itd.hmi.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.Route;
import hirain.itd.hmi.demo.dao.RouteMapper;
import hirain.itd.hmi.demo.service.IRouteService;

@Service
public class RouteService implements IRouteService {
	@Autowired
	private RouteMapper routeMapper;

	@Override
	public Route selectRouteById(int id) throws Exception {
		return routeMapper.selectRouteById(id);
	}

	@Override
	public List<Route> selectRoutesByCarId(int carId) throws Exception {
		return routeMapper.selectRoutesByCarId(carId);
	}
}
