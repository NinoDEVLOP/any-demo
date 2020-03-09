package com.fun.learn.base.interceptor;

import com.fun.learn.base.GlobalErrorEnum;
import com.fun.learn.base.RtnInfo;
import com.fun.learn.base.tools.UserContext;
import com.fun.learn.global.GlobalConstants;
import com.fun.learn.modules.login.model.entry.CustomerInfo;
import com.fun.learn.modules.login.tools.SsoJwtTools;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检验签名
 *
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/1 11:19
 */
@Component
@Slf4j
public class SignInterceptor extends HandlerInterceptorAdapter {

    @Value("${service.redirectUrl}")
    private String redirectUrl;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        log.info("处理地址:{}", request.getServletPath());
        Cookie[] cookies = request.getCookies();
        UserContext.putCookies(cookies);
        String jwtStr = UserContext.getCookies().get(GlobalConstants.COOKIE_KEY);
        if (!StringUtils.isEmpty(jwtStr)) {
            try {
                return verifyAndSetUserInfo(jwtStr);
            } catch (Exception e) {
                log.error("验证签名时异常", e);
            } finally {
                redirectLoginPage(response);
            }
        }
        //setSignatureErrorInfo(response);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }

    private boolean verifyAndSetUserInfo(String jwtStr) {
        try {
            //{userInfo:{},token:{}}
            String payloadJson = SsoJwtTools.verifyAndGetPayLoad(jwtStr);
            JsonElement jsonElement = new JsonParser().parse(payloadJson);
            //获取jwt标识token
            String tokenJson = jsonElement.getAsJsonObject().get(GlobalConstants.JWT_TOKEN).getAsString();
            String username = redisTemplate.opsForValue().get(tokenJson).toString();
            if (StringUtils.isEmpty(username)) {
                return false;
            }
            JsonElement userInfoJson = jsonElement.getAsJsonObject().get(GlobalConstants.JWT_USER_INFO);
            CustomerInfo info = new Gson().fromJson(userInfoJson.getAsString(), CustomerInfo.class);

            if (info.getUsername() != null && info.getUsername().equals(username)) {
                UserContext.setToken(tokenJson);
                UserContext.putUser(info);
                return true;
            }

        } catch (Exception e) {
            log.error("验证JWT签名时异常", e);
        }
        return false;
    }

    private void redirectLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect(redirectUrl);
        //setSignatureErrorInfo(response);
    }

    private void setSignatureErrorInfo(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        Gson gson = new Gson();
        String rtnInfo = gson.toJson(RtnInfo.error(GlobalErrorEnum.AUTH_REJECT_ERROR));
        outputStream.write(rtnInfo.getBytes());
    }
}
