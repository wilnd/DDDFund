package com.finpoints.bss.fund.domain.model.wallet;

public enum FrozenType {

    WITHDRAWAL("出金"),
    WITHDRAWAL_CANCEL("出金取消"),
    
    DEPOSIT("入金"),
    ;

    private final String desc;

    FrozenType(String desc) {
        this.desc = desc;
    }


}
