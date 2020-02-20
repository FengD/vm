package hirain.itd.hmi.demo.service;

import java.util.List;

import hirain.itd.hmi.demo.bean.Route;
import hirain.itd.hmi.demo.bean.vo.PageBean;

public interface IRouteService {

	Route selectRouteById(int id) throws Exception;

	List<Route> selectRoutesByCarId(int carId) throws Exception;

	PageBean selectAllRoutes(String route_name,String project_name, int pageCode,int  pageSize)throws Exception;

	int deleteRouteById(int routeId) throws Exception;

	int insert(Route route)throws Exception;

    int updateRouteById(Route route)throws Exception;
}
