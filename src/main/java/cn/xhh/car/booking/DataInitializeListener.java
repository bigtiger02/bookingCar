package cn.xhh.car.booking;

import cn.xhh.car.booking.entity.CarModel;
import cn.xhh.car.booking.repository.CarModelRepository;
import javax.annotation.Resource;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Component
public class DataInitializeListener implements ApplicationListener<ApplicationStartedEvent> {

    @Resource
    private CarModelRepository carModelRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        CarModel carModel1 = new CarModel();
        carModel1.setId(1L);
        carModel1.setName("Toyota Camry");
        carModel1.setStockAmount(2);
        carModel1.setVersion(1);
        carModelRepository.save(carModel1);

        CarModel carModel2 = new CarModel();
        carModel2.setId(2L);
        carModel2.setName("BMW 650");
        carModel2.setStockAmount(2);
        carModel2.setVersion(1);
        carModelRepository.save(carModel2);
    }
}
