package com.finpoints.bss.fund.port.adapter.restapi;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoBodyWarp {
    boolean nowarp() default true;
}
