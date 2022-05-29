package cn.xhh.car.booking.service;

import cn.xhh.car.booking.Controller.response.LoginResponse;
import cn.xhh.car.booking.exception.BizException;
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
public class UserServiceTest {

    @Resource
    private UserService userService;

    @BeforeAll
    public void setUp(){
        userService.register("test_user", "e10adc3949ba59abbe56e057f20f883e");

    }
    @Test
    public void should_register_fail_when_user_name_exist(){
        Assertions.assertThrows(BizException.class,
            ()->userService.register("test_user", "e10adc3949ba59abbe56e057f20f883e"),
            "用户名被占用");
    }

    @Test
    public void should_register_success_when_user_name_not_exist(){
        Assertions.assertDoesNotThrow(
            ()->userService.register("test_user1", "e10adc3949ba59abbe56e057f20f883e")
        );

        Assertions.assertDoesNotThrow(
            ()-> userService.login("test_user1", "e10adc3949ba59abbe56e057f20f883e")
        );
    }

    @Test
    public void should_login_failure_when_password_not_correct(){
        Assertions.assertThrows(BizException.class,
            ()->userService.login("test_user", "aaaaaaa"),
            "用户或密码不正确");
    }

    @Test
    public void should_login_failure_when_user_name_not_exist(){
        Assertions.assertThrows(BizException.class,
            ()->userService.login("test_user2", "e10adc3949ba59abbe56e057f20f883e"),
            "用户或密码不正确");
    }

    @Test
    public void should_login_success_when_both_user_name_and_password_are_correct(){
        Assertions.assertDoesNotThrow(
            ()->userService.login("test_user", "e10adc3949ba59abbe56e057f20f883e")
        );
    }

    @Test
    public void should_logout_success_when_token_is_valid(){
        LoginResponse resp = userService.login("test_user", "e10adc3949ba59abbe56e057f20f883e");
        Assertions.assertDoesNotThrow(()->userService.logout(resp.getToken()));
    }
}
