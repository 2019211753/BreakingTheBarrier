package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 普通的操作失败抛出的异常
 *
 * @author 山水夜止, Youngless
 * @version 1.1
 * @date 2022/1/7
 */
public class FailedOperationException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.FAILED_OPERATION;

    public FailedOperationException() {
        super();
    }

    public FailedOperationException(String errorMsg) {
        super(errorMsg);
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
