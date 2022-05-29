package cn.xhh.car.booking.service;

import cn.xhh.car.booking.Controller.response.LoginResponse;
import cn.xhh.car.booking.entity.CarModel;
import cn.xhh.car.booking.exception.BizException;
import cn.xhh.car.booking.repository.CarModelRepository;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingServiceTest {

    @Resource
    private CarModelRepository carModelRepository;
    @Resource
    private BookingService bookingService;
    @Resource
    private UserService userService;

    private LoginResponse loginResponse;
    private LoginResponse loginResponse2;

    private CarModel noStock;
    private CarModel stock1_1;
    private CarModel stock1_2;
    private CarModel sufficientStock;

    @BeforeAll
    public void setUp(){
        userService.register("test_booking","e10adc3949ba59abbe56e057f20f883e");
        userService.register("test_booking2","e10adc3949ba59abbe56e057f20f883e");
        this.loginResponse = userService.login("test_booking", "e10adc3949ba59abbe56e057f20f883e");
        this.loginResponse2 = userService.login("test_booking2", "e10adc3949ba59abbe56e057f20f883e");

        noStock = carModelRepository.save(buildCarModel("无库存汽车", 0));
        stock1_1 = carModelRepository.save(buildCarModel("汽车（库存1）_1", 1));
        stock1_2 = carModelRepository.save(buildCarModel("汽车（库存1）_2", 1));
        sufficientStock = carModelRepository.save(buildCarModel("汽车（库存充足）", 100));
    }

    private CarModel buildCarModel(String name, Integer stockAmount){
        CarModel carModel = new CarModel();
        carModel.setName(name);
        carModel.setStockAmount(stockAmount);
        carModel.setVersion(1);
        return carModel;
    }

    @Test
    public void should_book_failure_when_car_model_id_is_not_exist(){
        Assertions.assertThrows(BizException.class,
            ()-> bookingService.bookCar(88888888L, loginResponse.getUserId()),
            "未查询到该车型"
        );
    }
    @Test
    public void should_book_failure_when_car_model_stock_is_zero(){
        Assertions.assertThrows(BizException.class,
                ()-> bookingService.bookCar(noStock.getId(), loginResponse.getUserId()),
                "库存不足"
        );
    }

    @Test
    public void should_book_failure_when_car_model_stock_is_insufficient(){
        Long bookingOrderId = bookingService.bookCar(stock1_1.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId, "汽车预定失败");
        Assertions.assertThrows(BizException.class,
                ()-> bookingService.bookCar(stock1_1.getId(), loginResponse.getUserId()),
                "库存不足"
        );
    }

    @Test
    public void should_book_success_when_car_model_stock_is_sufficient(){
        final Long bookingOrderId = bookingService.bookCar(sufficientStock.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId, "汽车预定失败");
    }

    @Test
    public void should_book_success_when_car_model_return_back(){
        final Long bookingOrderId = bookingService.bookCar(stock1_2.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId, "汽车预定失败");
        Assertions.assertDoesNotThrow(
                ()->bookingService.returnCar(bookingOrderId, loginResponse.getUserId())
        );

        Long bookingOrderId1 = bookingService.bookCar(stock1_2.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId1, "汽车预定失败");
    }

    @Test
    public void should_return_back_failure_when_booking_order_not_exist(){
        Assertions.assertThrows(
                BizException.class,
                ()->bookingService.returnCar(88888888L, loginResponse.getUserId()),
                "未找到订单"
        );
    }

    @Test
    public void should_return_back_failure_when_the_user_is_not_the_owner_of_booking_order(){
        Long bookingOrderId = bookingService.bookCar(sufficientStock.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId, "汽车预定失败");
        Assertions.assertThrows(
                BizException.class,
                ()->bookingService.returnCar(bookingOrderId, loginResponse2.getUserId()),
                "订单不属于你"
        );
    }

    @Test
    public void should_return_back_failure_when_booking_order_has_finished(){
        Long bookingOrderId = bookingService.bookCar(sufficientStock.getId(), loginResponse.getUserId());
        Assertions.assertNotNull(bookingOrderId, "汽车预定失败");
        Assertions.assertDoesNotThrow(
                ()->bookingService.returnCar(bookingOrderId, loginResponse.getUserId())
        );

        Assertions.assertThrows(
                BizException.class,
                ()->bookingService.returnCar(bookingOrderId, loginResponse.getUserId()),
                "该订单已完成"
        );
    }
}
