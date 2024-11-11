package com.finpoints.bss.fund.domain.model.payment;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    BANK("本地银行"),

    WIRE(""),
    ;

    private final String desc;

    PaymentMethod(String desc) {
        this.desc = desc;
    }
}
