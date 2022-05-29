package cn.xhh.car.booking.Controller;

import cn.xhh.car.booking.Controller.response.LoginResponse;
import cn.xhh.car.booking.service.UserService;
import cn.xhh.car.booking.util.HttpResult;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public HttpResult<Void> register(String userName, String password){
        if(StringUtils.isBlank(userName)){
            return HttpResult.failure("User name is empty.", null);
        }

        if(StringUtils.isBlank(password)){
            return HttpResult.failure("Password is empty.", null);
        }

        userService.register(userName, password);
        return HttpResult.success(null);
    }

    @PostMapping("/login")
    public HttpResult<LoginResponse> login(String userName,String password){
        if(StringUtils.isBlank(userName)){
            return HttpResult.failure("User name is empty.", null);
        }

        if(StringUtils.isBlank(password)){
            return HttpResult.failure("Password is empty.", null);
        }

        LoginResponse resp = userService.login(userName, password);
        return HttpResult.success(resp);
    }

    @PostMapping("/logout")
    public HttpResult<Void> logout(HttpServletRequest request){
        String token = request.getHeader("token");
        userService.logout(token);
        return HttpResult.success(null);
    }
}
