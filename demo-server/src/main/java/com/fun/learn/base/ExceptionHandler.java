package com.fun.learn.base;

import com.fun.learn.base.exception.BaseException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 16:32
 */
@Slf4j
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RtnInfo rtnInfo;
        if (!(ex instanceof BaseException)) {
            log.error("捕获到未处理异常", ex);
            rtnInfo = RtnInfo.error(GlobalErrorEnum.UNKNOWN_ERROR);
        } else {
            rtnInfo = RtnInfo.error((BaseException) ex);
        }
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            synchronized (stream) {
                stream.write(new Gson().toJson(rtnInfo).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
