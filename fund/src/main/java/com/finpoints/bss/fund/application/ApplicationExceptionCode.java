package com.finpoints.bss.fund.application;

import com.finpoints.bss.common.ExceptionCode;
import org.springframework.http.HttpStatus;

public enum ApplicationExceptionCode implements ExceptionCode {

    // Global
    OK(HttpStatus.OK, "200"),
    BadRequest(HttpStatus.BAD_REQUEST, "BadRequest"),
    IllegalState(HttpStatus.BAD_REQUEST, "IllegalState"),
    Unauthorized(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    Forbidden(HttpStatus.FORBIDDEN, "Forbidden"),
    NotFound(HttpStatus.NOT_FOUND, "NotFound"),
    InternalServerError(HttpStatus.INTERNAL_SERVER_ERROR, "InternalServerError"),
    NotImplemented(HttpStatus.NOT_IMPLEMENTED, "NotImplemented"),

    ;

    private final HttpStatus status;
    private final String code;

    ApplicationExceptionCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String code() {
        return this.code;
    }
}
