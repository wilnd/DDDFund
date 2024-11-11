package com.finpoints.bss.fund.domain.model.payment;

import lombok.Getter;

@Getter
public enum PaymentOrderType {

    MANUAL("Manual Payment"),
    AUTOMATIC("Automatic Payment"),
    ;
    private final String desc;

    PaymentOrderType(String desc) {
        this.desc = desc;
    }
}
