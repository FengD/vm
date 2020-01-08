package hirain.itd.hmi.demo.service;

import java.util.List;

import hirain.itd.hmi.demo.bean.Route;

public interface IRouteService {

	Route selectRouteById(int id) throws Exception;

	List<Route> selectRoutesByCarId(int carId) throws Exception;
}
