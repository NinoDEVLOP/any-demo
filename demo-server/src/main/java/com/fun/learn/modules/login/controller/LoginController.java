package com.fun.learn.modules.login.controller;

import com.fun.learn.base.GlobalErrorEnum;
import com.fun.learn.base.RtnInfo;
import com.fun.learn.base.tools.UserContext;
import com.fun.learn.global.GlobalConstants;
import com.fun.learn.modules.login.model.dto.SignInDto;
import com.fun.learn.modules.login.model.entry.CustomerInfo;
import com.fun.learn.modules.login.service.JWTManagerService;
import com.fun.learn.modules.login.service.UserService;
import com.fun.learn.modules.login.tools.QrcodeTools;
import com.fun.learn.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/02/2020/2/29 17:55
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private JWTManagerService jwtManagerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${service.qrcodeLoginPath}")
    private String configQrcodeRedirectPath;

    @RequestMapping("/normal/signIn")
    @ResponseBody
    public RtnInfo signIn(SignInDto infoDto, HttpServletResponse response) {
        if (StringUtils.isEmpty(infoDto.getUsername()) || StringUtils.isEmpty(infoDto.getPassword())) {
            return RtnInfo.error(GlobalErrorEnum.PARAM_LOST_ERROR);
        }
        CustomerInfo info = userService.selectCustomerBySignInInfo(infoDto);
        String jwtStr = jwtManagerService.signInForJWT(info);
        Cookie cookie = CookieUtil.create(GlobalConstants.COOKIE_KEY, jwtStr);
        response.addCookie(cookie);
        return RtnInfo.SUCCESS_RES;
    }

    @RequestMapping("/qrcode/signIn")
    public void getSignInQrcode(HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        String redirectPath = (String) redisTemplate.opsForValue().get(GlobalConstants.QRCODE_REDIRECT_PATH_KEY);
        if (StringUtils.isEmpty(redirectPath)) {
            redirectPath = configQrcodeRedirectPath;
        }
        String uniqueCode = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(uniqueCode, "");
        redisTemplate.expire(uniqueCode, 300, TimeUnit.SECONDS);
        sb.append(redirectPath).append("/qrcode/comfirm?qrcodeNo=").append(uniqueCode);
        response.setHeader("qrcodeUuid", uniqueCode);
        try {
            QrcodeTools.encode(sb.toString(), response.getOutputStream());
        } catch (IOException e) {
            log.error("回写二维码时异常", e);
        }
    }

    /**
     * 手机端访问确认
     *
     * @return
     */
    @RequestMapping("/qrcode/comfirm")
    @ResponseBody
    public RtnInfo getSignInQrcode(String qrcodeUuid) {
        if (redisTemplate.opsForValue().get(qrcodeUuid) == null) {
            return RtnInfo.error(GlobalErrorEnum.QRCODE_TIMEOUT_ERROR);
        }
        String jwtStr = UserContext.getCookies().get(GlobalConstants.COOKIE_KEY);
        redisTemplate.opsForValue().set(qrcodeUuid, jwtStr);
        redisTemplate.expire(qrcodeUuid, 300, TimeUnit.SECONDS);
        return RtnInfo.SUCCESS_RES;
    }

    @RequestMapping("/qrcode/check")
    @ResponseBody
    public RtnInfo checkQrcodeBindStatus(String qrcodeUuid, HttpServletResponse response) {
        String jwtStr = (String) redisTemplate.opsForValue().get(qrcodeUuid);
        if (StringUtils.isEmpty(jwtStr)) {
            return RtnInfo.FAILED_RES;
        }
        Cookie cookie = CookieUtil.create(GlobalConstants.COOKIE_KEY, jwtStr);
        response.addCookie(cookie);
        return RtnInfo.SUCCESS_RES;
    }

    @RequestMapping(name = "/signOut", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RtnInfo signOut() {
        jwtManagerService.signOut(UserContext.getToken());
        return RtnInfo.success();
    }

}
