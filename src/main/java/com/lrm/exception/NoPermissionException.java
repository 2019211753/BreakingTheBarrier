package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 访问未被授权资源时抛出的异常类
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class NoPermissionException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.NO_PERMISSION;

    public NoPermissionException() {
    }

    public NoPermissionException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public StatusCode getCode() {
        return code;
    }
}
