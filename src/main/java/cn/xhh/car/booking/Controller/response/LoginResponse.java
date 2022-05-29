package cn.xhh.car.booking.Controller.response;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public class LoginResponse {

    private Long userId;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
