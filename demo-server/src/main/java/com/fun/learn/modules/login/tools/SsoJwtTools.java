package com.fun.learn.modules.login.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * @author littlePart
 * @version 1.0
 * @date 2020/03/2020/3/3 16:37
 */
@Slf4j
public class SsoJwtTools {

    static Algorithm algorithm = Algorithm.HMAC512("123");

    //static ThreadLocal local;

    public static String create(Map<String, String> info) {
        JWTCreator.Builder builder = JWT.create();
        builder.withSubject("normal");
        for (String key : info.keySet()) {
            builder.withClaim(key, info.get(key));
        }
        return builder.sign(algorithm);
    }

//    public static boolean valid(String jwtStr) {
//        try {
//            DecodedJWT decode = JWT.decode(jwtStr);
//            algorithm.verify(decode);
//            local.set(decode);
//            return true;
//        } catch (SignatureVerificationException e) {
//            log.error("签名验证不通过,jwt = {}", jwtStr);
//        }
//        return false;
//    }

    public static String verifyAndGetPayLoad(String jwtStr) {
        //DecodedJWT decode = (DecodedJWT) local.get();
        DecodedJWT decode = JWT.decode(jwtStr);
        algorithm.verify(decode);
//        if (decode == null) {
//            throw new ServiceException(GlobalErrorEnum.AUTH_REJECT_ERROR);
//        }
//        local.remove();
//        local.get();
        byte[] bytes = Base64.getDecoder().decode(decode.getPayload());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        byte[] bby = Base64.getDecoder().decode("eyJzdWIiOiJub3JtYWwiLCJ1c2VySW5mbyI6IntcImlkXCI6MSxcInVzZXJuYW1lXCI6XCJ0ZXN0XCIsXCJpc0RlbGV0ZVwiOjB9IiwidG9rZW4iOiI0Njg4YTM3ZTUxZDQ0YTYzYjFjZmRiZDE1ZTg1OTVlNiJ9");
        System.out.println(new String(bby));
//        Map<String, String> map = new HashMap<>();
//        map.put("test", "signer");
//        String jwt = create(map);
//        System.out.println(jwt);
//        DecodedJWT decode = JWT.decode(jwt);
//        System.out.println(decode.getHeader());
//        System.out.println(decode.getPayload());
//        System.out.println(decode.getSignature());
//        Map<String, Claim> map1 = decode.getClaims();
    }

}
