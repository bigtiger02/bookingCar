package cn.xhh.car.booking.service;

import cn.xhh.car.booking.Controller.response.CarModelDTO;
import cn.xhh.car.booking.entity.CarModel;
import cn.xhh.car.booking.repository.CarModelRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Service
public class CarModelService {

    @Resource
    private CarModelRepository carModelRepository;

    public List<CarModelDTO> list(){
        List<CarModel> list = carModelRepository.findAll();
        return list.stream()
                    .map(CarModelDTO::new)
                    .collect(Collectors.toList());
    }
}
