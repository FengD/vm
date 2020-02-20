package hirain.itd.hmi.demo.controller;

import java.util.List;

import hirain.itd.hmi.demo.bean.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.Route;
import hirain.itd.hmi.demo.service.IRouteService;
import hirain.itd.hmi.demo.utils.Response;
import hirain.itd.hmi.demo.utils.geo.GaodeMapTransform;
import hirain.itd.hmi.demo.utils.geo.PostgisTransform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Used for route Action")
@RestController
@RequestMapping("/route")
public class RouteController {

	@Autowired
	private IRouteService routeService;

	@ApiOperation(value = "get all route of one car", notes = "get all route of one car")
	@GetMapping("/car/{carId}")
	/*@CarLoginToken*/
	public Object getAllRouteOfOneCar(@PathVariable int carId) throws Exception {
		Response response;
		List<Route> routes;
		try {
			routes = routeService.selectRoutesByCarId(carId);
			response = new Response(routes, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@ApiOperation(value = "get all the information of one route", notes = "get all the information of one route")
	@GetMapping("/{routeId}")
	/*@CarLoginToken*/
	public Object getOneRoute(@PathVariable int routeId) throws Exception {
		Route route;
		Response response;
		try {
			route = routeService.selectRouteById(routeId);
			String routePoints = route.getRoute_point();
			String geoPointList = GaodeMapTransform.points(PostgisTransform.transformMultiPointStr(routePoints));
			route.setRoute_point(geoPointList);
			route.setStart_point(PostgisTransform.transformPointStr(route.getStart_point()).toString());
			route.setEnd_point(PostgisTransform.transformPointStr(route.getEnd_point()).toString());
			response = new Response(route, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@ApiOperation(value = "get all the information of every routes ", notes = "get all the information of every routes")
	@GetMapping("")
	/*@CarLoginToken*/
	public Object getAllRoutes(
								@RequestParam(value = "route_name", required = false,defaultValue="") String route_name,
								@RequestParam(value = "project_name", required = false,defaultValue="") String project_name,
								@RequestParam(value = "pageCode", required = false, defaultValue = "1") int pageCode,
							   @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) throws Exception {
		PageBean route;
		Response response;
		try {
			route = routeService.selectAllRoutes(route_name,project_name,pageCode,pageSize);
			response = new Response(route, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@DeleteMapping("{routeId}")
	@ApiOperation(value = "delete route by id", notes = "delete route by id")
	public Object delete(@PathVariable int routeId) {
		Response response;
		try {
			routeService.deleteRouteById(routeId);
			response = new Response(null, HttpStatus.OK);
		} catch (Exception e) {
			response = new Response(null, HttpStatus.NOT_FOUND);
		}
		return response.getResponse();
	}

	@PostMapping("")
	@ApiOperation(value = "insert  route ", notes = "nsert  route")
	public Object insert(@RequestBody Route route){
		Response response;
		try {
			routeService.insert(route);
			response=new Response("success", HttpStatus.OK);
		}catch (Exception e)
		{
			e.printStackTrace();
			response=new Response("fail",HttpStatus.FOUND);
		}
		return response.getResponse();
	}

	@PutMapping("")
	@ApiOperation(value = "update  route  by id", notes = "update  route  by id")
	public Object updateRouteById(@RequestBody Route route){
		Response response;
		try {
			routeService.updateRouteById(route);
			response=new Response("success", HttpStatus.OK);
		}catch (Exception e)
		{
			e.printStackTrace();
			response=new Response("fail",HttpStatus.FOUND);
		}
		return response.getResponse();
	}
}
