package com.fun.learn.base.exception;

import com.fun.learn.base.GlobalErrorEnum;
import lombok.Getter;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 17:08
 */
public class BaseException extends RuntimeException {

    @Getter
    protected String code;

    @Getter
    protected String msg;

    @Getter
    protected Throwable throwable;

    public BaseException(GlobalErrorEnum enumObj) {
        this.code = enumObj.getCode();
        this.msg = enumObj.getCode();
    }

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(GlobalErrorEnum enumObj, Throwable throwable) {
        this.code = enumObj.getCode();
        this.msg = enumObj.getCode();
        this.throwable = throwable;
    }

    public BaseException(String code, String msg, Throwable throwable) {
        this.code = code;
        this.msg = msg;
        this.throwable = throwable;
    }

}
