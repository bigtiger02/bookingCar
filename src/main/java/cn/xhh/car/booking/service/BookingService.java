package cn.xhh.car.booking.service;

import cn.xhh.car.booking.entity.BookingOrder;
import cn.xhh.car.booking.entity.CarModel;
import cn.xhh.car.booking.enums.BookingOrderStatusEnum;
import cn.xhh.car.booking.exception.BizException;
import cn.xhh.car.booking.repository.BookingOrderRepository;
import cn.xhh.car.booking.repository.CarModelRepository;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Service
public class BookingService {

    @Resource
    private BookingOrderRepository bookingOrderRepository;
    @Resource
    private CarModelRepository carModelRepository;

    @Transactional(rollbackFor = Throwable.class)
    public Long bookCar(Long carModelId, Long userId){
        Optional<CarModel> result = carModelRepository.findById(carModelId);
        if(!result.isPresent()){
            throw new BizException("未查询到该车型");
        }

        CarModel carModel = result.get();
        if(carModel.getStockAmount() < 1){
            throw new BizException("库存不足");
        }

        Integer count = carModelRepository.decrementStock(carModelId, carModel.getVersion());
        if(count != 1){
            throw new BizException("库存扣减失败");
        }

        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setCarModelId(carModelId);
        bookingOrder.setUserId(userId);
        bookingOrder.setBookingTime(new Date());
        bookingOrder.setStatus(BookingOrderStatusEnum.BOOKING.getCode());
        bookingOrder = bookingOrderRepository.save(bookingOrder);
        return bookingOrder.getId();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void returnCar(Long bookingOrderId, Long userId){
        Optional<BookingOrder> result = bookingOrderRepository.findById(bookingOrderId);
        if(!result.isPresent()){
            throw new BizException("未找到订单");
        }

        BookingOrder bookingOrder = result.get();
        if(!Objects.equals(bookingOrder.getUserId(), userId)){
            throw new BizException("订单不属于你");
        }

        if(!Objects.equals(bookingOrder.getStatus(), BookingOrderStatusEnum.BOOKING.getCode())){
            throw new BizException("该订单已完成");
        }

        Optional<CarModel> carModelResult = carModelRepository.findById(bookingOrder.getCarModelId());
        if(!result.isPresent()){
            throw new BizException("未查询到该车型");
        }

        Integer count =bookingOrderRepository.changeStatus(bookingOrder.getId(), bookingOrder.getStatus(), new Date());
        if(count != 1){
            throw new BizException("还车失败");
        }

        CarModel carModel = carModelResult.get();
        count = carModelRepository.incrementStock(bookingOrder.getCarModelId(), carModel.getVersion());
        if(count != 1){
            throw new BizException("库存增加失败");
        }
    }
}
