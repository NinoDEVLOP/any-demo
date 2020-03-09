package com.fun.learn.global;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 20:54
 */
public interface GlobalConstants {

    String COOKIE_KEY = "access_jwt";

    String JWT_USER_INFO = "userInfo";
    String JWT_TOKEN = "token";

    String QRCODE_REDIRECT_PATH_KEY = "qrcodeRedirectPath";

    int NORMAL_RETRY = 3;
    /**
     * 删除标识
     */
    int IS_DELETE = 1;
    /**
     * 未删除标识
     */
    int NOT_DELETE = 0;

}
