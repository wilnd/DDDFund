package com.finpoints.bss.fund.domain.model.payment;

import lombok.Getter;

@Getter
public enum PaymentBusinessType {

    WITHDRAWAL("提现"),
    ;

    private final String desc;

    PaymentBusinessType(String desc) {
        this.desc = desc;
    }
}
