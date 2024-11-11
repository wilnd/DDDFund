package com.finpoints.bss.fund.domain.model.payment;

import lombok.Getter;

@Getter
public enum PaymentOrderStatus {

    PENDING("待付款"),
    REMITTING("付款中"),
    SUCCESSFUL("付款成功"),
    FAILED("付款失败"),

    ;
    private final String desc;

    PaymentOrderStatus(String desc) {
        this.desc = desc;
    }
}
