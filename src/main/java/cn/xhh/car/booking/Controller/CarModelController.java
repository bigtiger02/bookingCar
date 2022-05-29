package cn.xhh.car.booking.Controller;

import cn.xhh.car.booking.Controller.response.CarModelDTO;
import cn.xhh.car.booking.Controller.response.CarModelListResponse;
import cn.xhh.car.booking.service.CarModelService;
import cn.xhh.car.booking.util.HttpResult;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@RestController
@RequestMapping("/carModel")
public class CarModelController {

    @Resource
    private CarModelService carModelService;

    @GetMapping("/list")
    public HttpResult<CarModelListResponse> list(){
        List<CarModelDTO> list = carModelService.list();
        CarModelListResponse resp = new CarModelListResponse(list);
        return HttpResult.success(resp);
    }
}
