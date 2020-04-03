package com.fun.learn.base.tools;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LittlePart
 * @version 1.0
 * @date 2020/03/2020/3/11 11:17
 */
public class RateContext {

    private static AtomicLong global = new AtomicLong(0L);

    private static ConcurrentHashMap<String, AtomicLong> pathCountMap = new ConcurrentHashMap<>();

    public static void add() {
        global.incrementAndGet();
        String servletPath = getServletPath();
        AtomicLong pathCount = pathCountMap.get(servletPath);
        if (pathCount == null) {
            pathCountMap.put(servletPath, new AtomicLong(1L));
        } else {
            pathCount.incrementAndGet();
        }
    }

    public static long getGlobal() {
        return global.get();
    }

    public static long getPath() {
        AtomicLong count = pathCountMap.get(getServletPath());
        return count == null ? 0L : count.get();
    }

    private static String getServletPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getServletPath();
    }

    public static void clear() {
        global.intValue();
        global.incrementAndGet();
        AtomicLong count = pathCountMap.get(getServletPath());
        if (count != null) {
            count.intValue();
        }
    }

}
