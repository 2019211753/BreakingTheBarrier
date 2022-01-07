package com.lrm.exception;

import com.lrm.enumeration.StatusCode;

/**
 * 访问不存在的资源时抛出的异常类
 *
 * @author 山水夜止, Youngless
 * @version 1.1
 * @date 2022/1/7
 */
public class NotFoundException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.NOT_FOUND;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String errorMsg) {
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
