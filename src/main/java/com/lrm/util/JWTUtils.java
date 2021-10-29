package com.lrm.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Map;

/**
 * 包装token令牌
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
@Component
public class JWTUtils {
    /**
     * 随机盐
     */
    private static String TOKEN;

    @Value("${token-salt}")
    public String tempToken = "";

    @PostConstruct
    public void init() {
        TOKEN = tempToken;
    }

    /**
     * 生成token
     *
     * @param map  传入payload
     * @return 返回token
     */
    public static String getToken(Map<String, String> map){
        JWTCreator.Builder builder = JWT.create();
        //添加到token
        map.forEach(builder::withClaim);
        //10小时之后过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY, 10);
        //添加到token
        builder.withExpiresAt(instance.getTime());
        //添加标签 返回token
        return builder.sign(Algorithm.HMAC256(TOKEN));
    }

    /**
     * 验证token
     *
     * @param token 令牌
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * @param token 令牌
     * @return 获取token中payload
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
