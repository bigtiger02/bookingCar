package cn.xhh.car.booking;

import cn.xhh.car.booking.exception.AuthException;
import cn.xhh.car.booking.exception.BizException;
import cn.xhh.car.booking.util.HttpResult;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public HttpResult<Void> bizExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("business exception.",e);
        return HttpResult.failure(e.getMessage(), null);
    }

    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public HttpResult<Void> authExceptionHandler(HttpServletRequest req, BizException e){
        logger.error("auth exception.",e);
        return HttpResult.failure("无权访问", null);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public HttpResult<Void> exceptionHandler(HttpServletRequest req, Throwable e){
        logger.error("business exception.",e);
        return HttpResult.failure("server went wrong, please wait.", null);
    }
}
