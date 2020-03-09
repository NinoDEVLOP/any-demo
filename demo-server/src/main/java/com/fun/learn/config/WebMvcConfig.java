package com.fun.learn.config;

import com.fun.learn.base.interceptor.SignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 17:47
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SignInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns("/**/signIn", "/error","/qrcode/check");
    }
}
