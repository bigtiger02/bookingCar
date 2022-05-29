package cn.xhh.car.booking.exception;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
public class BizException extends RuntimeException{

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
