package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 访问不存在的资源时抛出的异常类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class NotFoundException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.NOT_FOUND;

    public NotFoundException() {
    }

    public NotFoundException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public StatusCode getCode() {
        return code;
    }
}
