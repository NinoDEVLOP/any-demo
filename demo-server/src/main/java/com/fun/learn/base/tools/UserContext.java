package com.fun.learn.base.tools;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 16:23
 */
public class UserContext {

    private static final ThreadLocal<Object> USER_INFO = new ThreadLocal<>();
    private static final ThreadLocal<Object> COOKIES = new ThreadLocal<>();
    private static final ThreadLocal<Object> TOKEN = new ThreadLocal<>();

    public static String getToken() {
        return (String) TOKEN.get();
    }

    public static void setToken(String token) {
        TOKEN.set(token);
    }

    public static Object getUser() {
        return USER_INFO.get();
    }

    public static <T> void putUser(T user) {
        USER_INFO.set(user);
    }

    public static Map<String, String> getCookies() {
        return (Map<String, String>) COOKIES.get();
    }

    public static void putCookies(Cookie[] cookies) {
        HashMap<String, String> map = new HashMap<>(cookies.length);
        Arrays.stream(cookies).forEach(cookie -> map.put(cookie.getName(), cookie.getValue()));
        COOKIES.set(map);
    }

    public static void clear() {
        USER_INFO.remove();
        USER_INFO.get();
        COOKIES.remove();
        COOKIES.get();
        TOKEN.remove();
        TOKEN.get();
    }
}
