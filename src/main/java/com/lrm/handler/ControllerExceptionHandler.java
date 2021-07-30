package com.lrm.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lrm.exception.CommonException;
import com.lrm.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 为了不将抛出的异常直接暴露给用户
 * 全局异常处理 扫描所有的controller类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */

@RestControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @param e 自定义四种异常类的公共父类 通过多态简化为一个Handler
     * @return 给前端展示的结果 code为407-403
     */
    @ExceptionHandler(CommonException.class)
    public Result CommonExceptionHandler(HttpServletRequest request, CommonException e) {
        logger.error("Request URL: {}, exception : {}", request.getRequestURL(), e);
        return Result.returnCommonException(e, request.getRequestURL());
    }

    /**
     * 前端访问的API不存在抛出的异常
     *
     * @return 给前端展示的结果 code为404
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public Result handleNotFoundExceptions(HttpServletRequest request) {
        return Result.returnNoHandlerFoundException(request.getRequestURL());
    }

    /**
     * 负责输入输出流异常
     *
     * @param request 获取请求发送的资源 URL
     * @return 给前端展示的结果 code为402
     */
    @ExceptionHandler({IOException.class})
    public Result IOEHandler(HttpServletRequest request, IOException e) {
        logger.error("Request URL: {}, exception : {}", request.getRequestURL(), e);
        return Result.returnIOAndMaxSizeException(request.getRequestURL());
    }

    /**
     * 负责处理上传文件过大异常
     *
     * @param request 获取请求发送的资源 URL
     * @return 给前端展示的结果 code为402
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public Result IOEHandler(HttpServletRequest request, MaxUploadSizeExceededException e) {
        logger.error("Request URL: {}, exception : {}", request.getRequestURL(), e);
        return Result.returnIOAndMaxSizeException(request.getRequestURL());
    }

    /**
     * 负责处理JWT异常
     *
     * @param request 获取请求发送的资源 URL
     * @param e JWT鉴权异常
     * @return 给前端展示的结果 code为401
     */
    @ExceptionHandler(JWTVerificationException.class)
    public Result JWTHandler(HttpServletRequest request, JWTVerificationException e) {
        logger.error("Request URL: {}, exception : {}", request.getRequestURL(), e);
        return Result.returnJWTException(request.getRequestURL());
    }

    /**
     * 负责处理不知道拿来的异常
     *
     * @param request 获取请求发送的资源 URL
     * @param e       其他异常
     * @return 给前端展示的结果 code为400
     * @throws Exception 其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {}, exception : {}", request.getRequestURL(), e);
        //排除我自定义的异常类 如果不加这个判断 会被error.html拦截 所以要判断一下ResponseStatus的"状态"
        if ((AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)) {
            throw e;
        }
        return Result.returnNotDefinedError(request.getRequestURL());
    }

}
