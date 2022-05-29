package cn.xhh.car.booking;

import cn.xhh.car.booking.service.UserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author kimluo
 * @version V1.0
 * @since 2022/5/29
 **/
@Component
@Order(1)
@WebFilter(urlPatterns = {"/*"})
public class ValidTokenFilter implements Filter {
    private static final List<String> IGNORED_PATHS = Collections.unmodifiableList(
            Arrays.asList("/user/login", "/user/register", "/carModel/list","/index")
    );

    @Resource
    private UserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        String url = ((HttpServletRequest)req).getRequestURI();
        boolean ignoreAuth = IGNORED_PATHS.stream()
                .anyMatch(path->StringUtils.startsWith(url, path));

        if(ignoreAuth){
            filterChain.doFilter(req, resp);
            return;
        }

        String token = ((HttpServletRequest) req).getHeader("token");
        if(StringUtils.isNotBlank(token)
                && Objects.nonNull(userService.getUserIdFrom(token))){
            filterChain.doFilter(req, resp);
            return;
        }

        HttpServletResponse response = (HttpServletResponse)resp;
        response.addHeader("content-type","application/json");
        response.getWriter().write("{\"code\":300, \"message\":\"NO PERMISSION\"}");
    }
}
