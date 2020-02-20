package hirain.itd.hmi.demo.serviceimpl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.bean.vo.RouteProject;
import hirain.itd.hmi.demo.utils.geo.GaodeMapTransform;
import hirain.itd.hmi.demo.utils.geo.PostgisTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hirain.itd.hmi.demo.bean.Route;
import hirain.itd.hmi.demo.dao.RouteMapper;
import hirain.itd.hmi.demo.service.IRouteService;

@Service
public class RouteService implements IRouteService {
	@Autowired
	private RouteMapper routeMapper;
	@Autowired
	private PageService pageService;
	@Override
	public Route selectRouteById(int id) throws Exception {
		return routeMapper.selectRouteById(id);
	}

	@Override
	public List<Route> selectRoutesByCarId(int carId) throws Exception {
		return routeMapper.selectRoutesByCarId(carId);
	}

	@Override
	public PageBean selectAllRoutes(String route_name,String project_name, int pageCode, int  pageSize)throws Exception{

		PageHelper.startPage(pageCode,pageSize);
		List<RouteProject> all=routeMapper.selectAllRoutes(route_name.trim(),project_name.trim());
		RouteProject route;
		for(int i=0;i<all.size();i++)
		{
			route=all.get(i);
			String routePoints = route.getRoute_point();
			String geoPointList = GaodeMapTransform.points(PostgisTransform.transformMultiPointStr(routePoints));
			route.setRoute_point(geoPointList);
			route.setStart_point(PostgisTransform.transformPointStr(route.getStart_point()).toString());
			route.setEnd_point(PostgisTransform.transformPointStr(route.getEnd_point()).toString());
		}
		Page<RouteProject> p=(Page<RouteProject>)all;
		return pageService.getBeanByPage(p,pageCode,pageSize);
	}

	@Override
	public int deleteRouteById(int routeId) throws Exception{
		return routeMapper.deleteRouteById(routeId);
	}

	@Override
	public int insert(Route route)throws Exception{
		route.setRoute_line("linestring("+route.getRoute_line()+")");
		route.setRoute_point("multipoint("+route.getRoute_point()+")");
		route.setStart_point("point("+route.getStart_point()+")");
		route.setEnd_point("point("+route.getEnd_point()+")");
		return routeMapper.insert(route);
	}

	@Override
    public	int updateRouteById(Route route)throws Exception{
		route.setRoute_line("linestring("+route.getRoute_line()+")");
		route.setRoute_point("multipoint("+route.getRoute_point()+")");
		route.setStart_point("point("+route.getStart_point()+")");
		route.setEnd_point("point("+route.getEnd_point()+")");
		return routeMapper.updateRouteById(route);
	}
}
