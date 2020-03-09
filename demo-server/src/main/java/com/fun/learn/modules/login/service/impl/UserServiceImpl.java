package com.fun.learn.modules.login.service.impl;

import com.fun.learn.global.GlobalConstants;
import com.fun.learn.modules.login.model.dto.SignInDto;
import com.fun.learn.modules.login.model.entry.CustomerInfo;
import com.fun.learn.modules.login.service.UserService;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 20:27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SQLManager sqlManager;

    @Override
    public CustomerInfo selectCustomerBySignInInfo(SignInDto inDto) {
        CustomerInfo customerInfo = sqlManager.lambdaQuery(CustomerInfo.class)
                .andEq(CustomerInfo::getUsername, inDto.getUsername())
                .andEq(CustomerInfo::getPassword, inDto.getPassword())
                .andEq(CustomerInfo::getIsDelete, GlobalConstants.NOT_DELETE)
                .single();
        return customerInfo;
    }

}
