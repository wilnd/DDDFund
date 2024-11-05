package com.finpoints.bss.fund.domain.model.mt;

import lombok.Getter;

@Getter
public enum MtRequestType {

    Withdrawal("提现"),

    ;
    private final String desc;

    MtRequestType(String desc) {
        this.desc = desc;
    }
}
