package com.jwang261.onlineshop.order.intercepter;

import com.jwang261.common.constant.AuthServerConstant;
import com.jwang261.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jwang261
 * @date 2021/1/15 1:02 AM
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {

    public static ThreadLocal<MemberRespVo> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberRespVo attribute = (MemberRespVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if(attribute == null){
            request.getSession().setAttribute("msg","You need to sign in at first");
            response.sendRedirect("http://auth.jwang261-shop.com/login.html");
            return false;
        }else{
            loginUser.set(attribute);
            return true;
        }

    }
}
