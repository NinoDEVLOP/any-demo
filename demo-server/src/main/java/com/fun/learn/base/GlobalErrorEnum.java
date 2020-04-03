package com.fun.learn.base;

import lombok.Getter;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 16:44
 */
public enum GlobalErrorEnum {
    PARAM_LOST_ERROR("100", "重要参数缺失"),

    AUTH_REJECT_ERROR("200", "权限不足"),

    TOKEN_REJECT_ERROR("201", "TOKEN已过期"),

    QTS_ERROR("300", "操作过于频繁，请稍后重试"),

    QRCODE_ERROR("400", "二维码识别有误"),

    QRCODE_TIMEOUT_ERROR("401", "二维码已过期"),

    SERVER_BUSY("9998", "服务器繁忙，请稍后重试"),

    UNKNOWN_ERROR("9999", "服务器未知异常");

    @Getter
    private String code;

    @Getter
    private String msg;

    GlobalErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
