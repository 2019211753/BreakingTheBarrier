package com.lrm.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrm.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 基于路径判断处理异常, 拦截普通用户进入管理页的行为
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在请求头中取得token

        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        //这里发生异常需要后面来处理
        JWTUtils.verify(token);
        DecodedJWT decodedJWT = JWTUtils.getToken(token);
        //在token中取得admin
        boolean admin = Boolean.parseBoolean(decodedJWT.getClaim("admin").asString());
        if (!admin) {
            logger.error("exception : {}", "拦截器：用户没有管理员权限");
            map.put("data", null);
            map.put("url", request.getRequestURL());
            map.put("msg", "您没有管理员权限");
            map.put("code", "403");
        } else {
            return true;
        }
        //异常处理
        //转化为json返回前端
        //方法声明时的throws Exception是抛出这里的异常
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
        }
    }
