package com.seven.common.security.exception;

import com.seven.common.core.enums.ResultCode;
import lombok.Getter;


@Getter
public class ServiceException extends RuntimeException {

    private ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
