package cn.xhh.car.booking.util;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public class HttpResult<T> {

    private static final int CODE_SUCCESS = 200;
    private static final int CODE_FAILURE = 300;

    private int code;
    private String message;
    private T data;

    public static <T> HttpResult<T> success(T data){
        HttpResult<T> result = new HttpResult();
        result.setCode(CODE_SUCCESS);
        result.setMessage("");
        result.setData(data);
        return result;
    }

    public static <T> HttpResult<T> failure(String msg, T data){
        HttpResult<T> result = new HttpResult();
        result.setCode(CODE_FAILURE);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
