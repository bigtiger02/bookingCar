package cn.xhh.car.booking.service;

import cn.xhh.car.booking.Controller.response.LoginResponse;
import cn.xhh.car.booking.entity.User;
import cn.xhh.car.booking.exception.BizException;
import cn.xhh.car.booking.repository.UserRepository;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Service
public class UserService {

    private static final Map<String, Long> TOKEN_MAP = new ConcurrentHashMap<>();

    @Resource
    private UserRepository userRepository;

    public void register(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if(Objects.nonNull(user)){
            throw new BizException("用户名被占用");
        }

        String salt = RandomStringUtils.random(8);
        String tPass = getSaltHash(userName, password, salt);
        user = new User();
        user.setUserName(userName);
        user.setPassword(tPass);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userRepository.save(user);
    }

    private String getSaltHash(String userName,String password,  String salt){
        String tmp = userName + salt + password;
        return DigestUtils.md5DigestAsHex(tmp.getBytes());
    }

    public LoginResponse login(String userName, String password) {
        User user = userRepository.findByUserName(userName);
        if(Objects.isNull(user)){
            throw new BizException("用户或密码不正确");
        }

        String tPass = getSaltHash(userName, password, user.getSalt());
        if(!StringUtils.equals(tPass, user.getPassword())){
            throw new BizException("用户或密码不正确");
        }

        String token = UUID.randomUUID().toString().replace("-","");
        TOKEN_MAP.put(token, user.getId());

        LoginResponse resp = new LoginResponse(user.getId(), token);
        return resp;
    }

    public Long getUserIdFrom(String token){
        return TOKEN_MAP.get(token);
    }

    public void logout(String token) {
        TOKEN_MAP.remove(token);
    }
}
