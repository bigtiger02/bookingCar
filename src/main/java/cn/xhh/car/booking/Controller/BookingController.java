package cn.xhh.car.booking.Controller;

import cn.xhh.car.booking.service.BookingService;
import cn.xhh.car.booking.service.UserService;
import cn.xhh.car.booking.util.HttpResult;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@RestController
@RequestMapping("/booking")
@Validated
public class BookingController {

    @Resource
    private BookingService bookingService;
    @Resource
    private UserService userService;

    @PostMapping("/bookCar")
    public HttpResult<Long> bookCar(
            @NotNull(message = "carModelId is null")Long carModelId,
            HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = userService.getUserIdFrom(token);
        Long bookingOrderId = bookingService.bookCar(carModelId, userId);
        return HttpResult.success(bookingOrderId);
    }

    @PostMapping("/returnCar")
    public HttpResult<Void> returnCar(
            @NotNull(message = "bookingOrderId is null")Long bookingOrderId,
            HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = userService.getUserIdFrom(token);
        bookingService.returnCar(bookingOrderId, userId);
        return HttpResult.success(null);
    }
}
