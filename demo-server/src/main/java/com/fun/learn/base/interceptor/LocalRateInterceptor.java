package com.fun.learn.base.interceptor;

import com.fun.learn.base.tools.RateContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 针对流量计数统计
 *
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/11 10:48
 */
public class LocalRateInterceptor extends HandlerInterceptorAdapter {

    //private static

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RateContext.add();
        return true;
    }
}
