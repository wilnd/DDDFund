package com.finpoints.bss.fund.application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private final ApplicationExceptionCode exceptionCode;
    private final Object[] args;

    public ApplicationException(ApplicationExceptionCode exceptionCode, Object... args) {
        this.exceptionCode = exceptionCode;
        this.args = args;
    }

    public ApplicationException(String message, ApplicationExceptionCode exceptionCode, Object... args) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.args = args;
    }

    public ApplicationException(String message, Throwable cause, ApplicationExceptionCode exceptionCode, Object... args) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
        this.args = args;
    }

    public ApplicationException(Throwable cause, ApplicationExceptionCode exceptionCode, Object... args) {
        super(cause);
        this.exceptionCode = exceptionCode;
        this.args = args;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                                ApplicationExceptionCode exceptionCode, Object... args) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionCode = exceptionCode;
        this.args = args;
    }

    public ApplicationExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public HttpStatus httpStatus() {
        return exceptionCode.status();
    }

    public String getCode() {
        return exceptionCode.code();
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getMessage() {
        return StringUtils.isBlank(super.getMessage()) ? exceptionCode.code() : super.getMessage();
    }
}
