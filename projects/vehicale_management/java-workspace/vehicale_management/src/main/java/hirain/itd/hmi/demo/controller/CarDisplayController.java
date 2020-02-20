package hirain.itd.hmi.demo.controller;

import hirain.itd.hmi.demo.annotation.CarLoginToken;
import hirain.itd.hmi.demo.bean.vo.CarProfile;
import hirain.itd.hmi.demo.service.ICarService;
import hirain.itd.hmi.demo.utils.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("carDisplay")
public class CarDisplayController {

    @Autowired
    private ICarService carService;
    @GetMapping("{carId}")
    @ApiOperation(value = "car", notes = "get car by id")
    @CarLoginToken
    public Object getCarById(@PathVariable int carId) {
        Response response;
        CarProfile car;
        try {
            car = carService.selectCarProfileById(carId);
            response = new Response(car, HttpStatus.OK);
        } catch (Exception e) {
            response = new Response(null, HttpStatus.NOT_FOUND);

        }
        return response.getResponse();
    }
}
