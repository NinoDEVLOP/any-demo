package com.fun.learn.modules.login.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fun.learn.base.GlobalErrorEnum;
import com.fun.learn.base.RtnInfo;
import com.fun.learn.base.exception.ServiceException;
import com.fun.learn.global.GlobalConstants;
import com.fun.learn.modules.login.model.entry.CustomerInfo;
import com.fun.learn.modules.login.service.JWTManagerService;
import com.fun.learn.modules.login.tools.SsoJwtTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 11:20
 */
@Slf4j
@Service
public class JWTManagerServiceImpl implements JWTManagerService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String signInForJWT(CustomerInfo info) {
        String tokenUuid = UUID.randomUUID().toString().replaceAll("-", "");
        String ticket = generateTicket(tokenUuid, info);
        boolean success = redisTemplate.opsForValue().setIfAbsent(tokenUuid, info.getUsername());
        if (success) {
            int i = 0;
            while (i < GlobalConstants.NORMAL_RETRY) {
                if (redisTemplate.expire(tokenUuid, 60, TimeUnit.MINUTES)) {
                    break;
                }
                i++;
            }
        }

        return ticket;
    }

    @Override
    public RtnInfo signOut(String tokenUUID) {
        if (!StringUtils.isEmpty(tokenUUID)) {
            redisTemplate.opsForValue().set(tokenUUID, "");
            redisTemplate.expire(tokenUUID, 1, TimeUnit.MILLISECONDS);
        }
        return RtnInfo.success();
    }

    /**
     * 生成ticket，ticket有效期与token一致
     *
     * @param customerInfo
     * @return
     */
    private String generateTicket(String tokenUUID, CustomerInfo customerInfo) {
        try {
            Map<String, String> map = new HashMap<>(1);
            ObjectMapper objectMapper = new ObjectMapper();
            map.put(GlobalConstants.JWT_USER_INFO, objectMapper.writeValueAsString(customerInfo));
            map.put(GlobalConstants.JWT_TOKEN, tokenUUID);
            return SsoJwtTools.create(map);
        } catch (Exception e) {
            log.error("生成JWT时异常");
            throw new ServiceException(GlobalErrorEnum.UNKNOWN_ERROR);
        }
    }
}
