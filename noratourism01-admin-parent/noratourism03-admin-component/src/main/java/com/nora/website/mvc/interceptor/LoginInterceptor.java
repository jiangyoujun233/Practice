package com.nora.website.mvc.interceptor;

import com.nora.website.constant.TourismConstant;
import com.nora.website.entity.Admin;
import com.nora.website.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//因为只需要做目标Handler的前置检查，所以继承HandlerInterceptorAdapter
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //1.通过request对象获取Session对象
        HttpSession session = httpServletRequest.getSession();

        //2.尝试从Session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(TourismConstant.ATTR_NAME_LOGIN_ADMIN);

        //3.判断admin对象是否为空
        if (admin == null){

            //4.抛出异常
            throw new AccessForbiddenException(TourismConstant.MESSAGE_ACCESS_FORBIDDEN);

        }

        //如果Admin对象不为null，则返回true放行
        return true;
    }
}
