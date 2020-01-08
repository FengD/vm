package hirain.itd.hmi.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hirain.itd.hmi.demo.bean.Route;

@Mapper
public interface RouteMapper {
	Route selectRouteById(int id);

	List<Route> selectRoutesByCarId(int carId);
}
