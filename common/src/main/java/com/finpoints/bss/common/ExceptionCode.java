package com.finpoints.bss.common;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    HttpStatus status();

    String code();
}
