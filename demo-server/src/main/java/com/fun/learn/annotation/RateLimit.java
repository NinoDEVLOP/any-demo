package com.fun.learn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/10 15:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RateLimit {

    /**
     * 是否开启
     *
     * @return
     */
    boolean limit() default true;

    /**
     * 针对用户频率
     * @return
     */
    boolean user() default true;

    /**
     * 最大速率时每秒可放行100次请求
     */
    int max() default 100;

    /**
     * 刚刚启动时每秒放行请求，默认为0，表示此属性未生效
     */
    int min() default -1;

}
