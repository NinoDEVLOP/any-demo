package com.fun.learn.base.interceptor;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 17:32
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginStatusInterceptor extends HandlerInterceptorAdapter {

    //@Value("${}")


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
