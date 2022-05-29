package cn.xhh.car.booking.repository;

import cn.xhh.car.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
}
