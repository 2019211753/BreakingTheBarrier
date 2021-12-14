package com.lrm.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrm.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于路径判断处理异常，拦截有问题的登录行为
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class JWTInterceptor implements HandlerInterceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        String requestURL = request.getRequestURI();
        try {
            logger.info("RequestURL: {} ", requestURL);
            logger.info("GetMethod: {}", handler);
            JWTUtils.verify(token);
            return true;
        //异常处理
        } catch (TokenExpiredException e) {
            logger.error("exception : {}", "拦截器：用户令牌已经过期");
            map.put("msg", "用户令牌已经过期，请重新登陆");
        } catch (SignatureVerificationException e){
            logger.error("exception : {}", "拦截器：签名错误");
            map.put("msg", "签名错误");
        } catch (AlgorithmMismatchException e){
            logger.error("exception : {}", "拦截器：加密算法不匹配");
            map.put("msg", "加密算法不匹配");
        } catch (NullPointerException e) {
            logger.error("exception : {}", "拦截器：令牌为空");
            map.put("msg", "令牌为空");
        } catch (Exception e) {
            logger.error("exception : {}", "拦截器：无效令牌");
            map.put("msg", "无效令牌");
        }
        map.put("code", "401");
        map.put("data", null);
        map.put("url", requestURL);
        //转化为json返回前端
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
    }
}
