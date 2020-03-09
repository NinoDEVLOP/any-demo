package com.fun.learn.base.exception;

import com.fun.learn.base.GlobalErrorEnum;
import lombok.Getter;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 16:39
 */
public class ServiceException extends BaseException {

    @Getter
    private String code;

    @Getter
    private String msg;

    public ServiceException(GlobalErrorEnum enumObj) {
        super(enumObj);
    }

    public ServiceException(String code, String msg) {
        super(code, msg);
    }

    public ServiceException(GlobalErrorEnum enumObj, Throwable throwable) {
        super(enumObj, throwable);
    }

    public ServiceException(String code, String msg, Throwable throwable) {
        super(code, msg, throwable);
    }
}
