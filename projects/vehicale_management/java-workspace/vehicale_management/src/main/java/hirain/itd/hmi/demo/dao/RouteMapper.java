package hirain.itd.hmi.demo.dao;

import java.util.List;

import hirain.itd.hmi.demo.bean.vo.RouteProject;
import org.apache.ibatis.annotations.Mapper;

import hirain.itd.hmi.demo.bean.Route;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RouteMapper {
	Route selectRouteById(int id);

	List<Route> selectRoutesByCarId(int carId);

    List<RouteProject> selectAllRoutes(@Param("route_name")String route_name, @Param("project_name")String project_name);

    int deleteRouteById(int routeId);

    int insert(Route route);

    int updateRouteById(Route route);

    int deleteRouteByProjectId(int projectId);

 }
