//package com.fun.learn.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.fun.learn.annotation.RateLimit;
//import com.fun.learn.base.tools.UserContext;
//import com.fun.learn.modules.login.model.entry.CustomerInfo;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.util.StringUtils;
//
//import java.lang.reflect.Method;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Semaphore;
//
///**
// * 普通漏桶限流器
// *
// * @author LittlePart
// * @version 1.0
// * @date 2020/03/2020/3/10 15:31
// */
//@Aspect
//public class RateLimitHandler {
//
//    private ConcurrentHashMap<Method, RateLimit> cacheMap = new ConcurrentHashMap<>();
//
//    private volatile ConcurrentHashMap<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();
//
//    //private Semaphore global = new Semaphore(1000);
//
//    private Timer timer;
//
//    public RateLimitHandler() {
//        timer = new Timer();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.MINUTE, 1);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                semaphoreMap.c
//            }
//        }, calendar.getTime(), 60000L);
//    }
//
//    @Pointcut("@annotation(com.fun.learn.annotation.RateLimit)")
//    public void pointcut() {
//    }
//
//    @Around("pointcut()")
//    public Object action(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            Method method = signature.getMethod();
//            RateLimit limit = cacheMap.get(method);
//            if (limit == null) {
//                limit = method.getAnnotation(RateLimit.class);
//                cacheMap.put(method, limit);
//            }
//            if (!limit.limit()) {
//                return joinPoint.proceed();
//            }
//            Semaphore semaphore = semaphoreMap.get(method);
//            if (semaphore == null) {
//                semaphore = new Semaphore(limit.max());
//                semaphoreMap.put(method.get, semaphore);
//            }
//
//            if (limit.user()) {
//                Semaphore userSemaphore = new Semaphore(limit.max());
//                CustomerInfo info = (CustomerInfo) UserContext.getUser();
//                semaphoreMap.put(method)
//            }
//
//            String url = request.getRequestURL().toString();
//            String uri = request.getRequestURI();
//            String ip = request.getRemoteAddr();
//            String classMethodPlace = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//            String args = JSON.toJSONString(joinPoint.getArgs());
//            OperationLog operationLog = OperationLog.builder()
//                    .url(url)
//                    .uri(uri)
//                    .ip(ip)
//                    .source(source)
//                    .classMethodPlace(classMethodPlace)
//                    .args(args)
//                    .methodDesc(StringUtils.hasText(action.name()) ? action.name() : "")
//                    .createTime(new Date())
//                    .createBy(createBy)
//                    .createName(StringUtils.hasText(createName) ? createName : "")
//                    .build();
//            operateLogService.addOperateLog(operationLog);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
