package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 类<code>Doc</code>用于：TODO
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-29
 */
public class CommonException extends RuntimeException {
    StatusCode code;
    String errorMsg;

    public CommonException() {
    }

    public CommonException(String errorMsg) {
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
