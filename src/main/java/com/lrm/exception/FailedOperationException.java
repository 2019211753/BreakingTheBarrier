package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 普通的操作失败抛出的异常
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-05-26
 */
public class FailedOperationException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.FAILED_OPERATION;

    public FailedOperationException() {

    }

    public FailedOperationException(String errorMsg) {
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
