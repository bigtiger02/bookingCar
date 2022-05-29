package cn.xhh.car.booking.repository;

import cn.xhh.car.booking.entity.BookingOrder;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Repository
public interface BookingOrderRepository extends JpaRepository<BookingOrder, Long> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update t_booking_order set status = 100, return_time =:returnTime where id = :id and status = :status")
    Integer changeStatus(Long id, Integer status, Date returnTime);
}
