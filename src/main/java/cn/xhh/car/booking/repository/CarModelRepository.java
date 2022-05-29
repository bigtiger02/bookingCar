package cn.xhh.car.booking.repository;

import cn.xhh.car.booking.entity.CarModel;
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
public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update t_car_model set stock_amount = stock_amount-1,version=version+1 where id = :id and version = :version")
    Integer decrementStock(Long id, Integer version);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update t_car_model set stock_amount = stock_amount+1,version=version+1 where id = :id and version = :version")
    Integer incrementStock(Long id, Integer version);
}
