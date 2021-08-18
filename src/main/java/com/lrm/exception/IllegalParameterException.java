package com.lrm.exception;

import com.lrm.enumeration.StatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 参数类型异常
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-29
 */
public class IllegalParameterException extends CommonException {
    protected String errorMsg;

    final StatusCode code = StatusCode.ILLEGAL_PARAMETER;

    public IllegalParameterException() {
    }

    public IllegalParameterException(String errorMsg) {
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

    public static String getMessage(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        StringBuilder buffer = new StringBuilder(64);
        for (ObjectError error : errors) {
            buffer.append(error.getDefaultMessage()).append("；");
        }
        return buffer.toString();
    }
}