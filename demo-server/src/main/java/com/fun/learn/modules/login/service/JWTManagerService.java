package com.fun.learn.modules.login.service;

import com.fun.learn.base.RtnInfo;
import com.fun.learn.modules.login.model.entry.CustomerInfo;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 11:20
 */
public interface JWTManagerService {

    String signInForJWT(CustomerInfo info);

    RtnInfo signOut(String ticket);

}
