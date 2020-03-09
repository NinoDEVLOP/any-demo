package com.fun.learn.utils;

import javax.servlet.http.Cookie;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/7 19:50
 */
public class CookieUtil {

    public static Cookie create(String key, String value) {
        return create(key, value, "/");
    }

    public static Cookie create(String key, String value, String path) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(false);
        //cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath(path);
        return cookie;
    }

}
