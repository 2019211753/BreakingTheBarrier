package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 多个异常的公有类
 *
 * @author 山水夜止, Youngless
 * @version 1.1
 * @date 2022/1/7
 */
public class CommonException extends RuntimeException {
    StatusCode code;
    String errorMsg;

    public CommonException() {
        super();
    }

    public CommonException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public StatusCode getCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
