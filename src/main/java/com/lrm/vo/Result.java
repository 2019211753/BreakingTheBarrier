package com.lrm.vo;

import com.lrm.enumeration.StatusCode;
import com.lrm.exception.CommonException;

/**
 * 包装类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class Result {

    Object data;

    Integer code;

    String msg;

    StringBuffer url;

    //正常类型的包装返回的结果
    public Result(Object data, String msg) {
        this.setData(data);
        this.setCode(StatusCode.SUCCESS);
        this.setMsg(msg);
    }

    public Result() {

    }

    public static Result returnIllegalRequestException(String msg, StringBuffer url) {
        Result result = new Result();
        result.setData(null);
        result.setMsg(msg);
        result.setUrl(url);
        result.setCode(StatusCode.ILLEGAL_REQUEST);
        return result;
    }

    public static Result returnCommonException(CommonException commonException, StringBuffer url) {
        Result result = new Result();
        result.setData(null);
        result.setMsg(commonException.getErrorMsg());
        result.setUrl(url);
        result.setCode(commonException.getCode());
        return result;
    }

    public static Result returnNoHandlerFoundException(StringBuffer url) {
        Result result = new Result();
        result.setData(null);
        result.setMsg("未查询到该API");
        result.setUrl(url);
        result.setCode(StatusCode.NOT_FOUND);
        return result;
    }

    public static Result returnIOAndMaxSizeException(StringBuffer url) {
        Result result = new Result();
        result.setData(null);
        result.setMsg("文件超过了1MB");
        result.setUrl(url);
        result.setCode(StatusCode.FAILED_TRANSFER);
        return result;
    }

    public static Result returnJWTException(StringBuffer url) {
        Result result = new Result();
        result.setData(null);
        result.setMsg("用户令牌无效");
        result.setUrl(url);
        result.setCode(StatusCode.WRONG_JWT);
        return result;
    }

    //其他未知异常返回的结果
    public static Result returnNotDefinedError(StringBuffer url){
        Result result = new Result();
        result.setData(null);
        result.setMsg("我也不知道发生甚么事了...");
        result.setUrl(url);
        result.setCode(StatusCode.UNKNOWN);
        return result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StringBuffer getUrl() {
        return url;
    }

    public void setUrl(StringBuffer url) {
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code.getCode();
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code.toString() +
                ", msg='" + msg + '\'' +
                ", url=" + url +
                '}';
    }

}