package com.fun.learn.modules.login.service;

import com.fun.learn.modules.login.model.dto.SignInDto;
import com.fun.learn.modules.login.model.entry.CustomerInfo;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 20:24
 */
public interface UserService {

    CustomerInfo selectCustomerBySignInInfo(SignInDto signInDto);

}
