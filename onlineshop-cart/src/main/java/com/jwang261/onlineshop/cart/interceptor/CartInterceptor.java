package com.jwang261.onlineshop.cart.interceptor;

import com.jwang261.common.constant.AuthServerConstant;
import com.jwang261.common.constant.CartConstant;
import com.jwang261.common.vo.MemberRespVo;
import com.jwang261.onlineshop.cart.vo.UserInfoTo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author jwang261
 * @date 2021/1/11 5:48 PM
 */
//执行目标方法之前判断用户的登录状态并封装传递给controller
@Component
public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfo = new UserInfoTo();
        HttpSession session = request.getSession();
        MemberRespVo member = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(member != null){
            //登录了
            userInfo.setUserId(member.getId());
        }
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(name.equals(CartConstant.TEMP_USER_COOKIE_NAME)){
                    userInfo.setUserKey(cookie.getValue());
                }
            }
        }
        //如果没有临时用户要分配一个临时用户
        if(StringUtils.isEmpty(userInfo.getUserKey())){
            String uuid = UUID.randomUUID().toString();
            userInfo.setUserKey(uuid);
        }
        threadLocal.set(userInfo);
        return true;
    }


    //业务执行之后保存cookie
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME,threadLocal.get().getUserKey());
        cookie.setDomain("jwang261-shop.com");
        cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
        response.addCookie(cookie);
    }
}
