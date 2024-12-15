package com.example.rommall.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("userId") == null){
            response.sendRedirect(request.getContextPath()+"/login.html");
            //用户未登录，拦截请求
            return false;
        }else{
            return true;
        }
    }
}

