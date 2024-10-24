package com.finpoints.bss.fund.domain.model.wallet;

public enum FrozenType {

    Withdrawal("出金"),
    WithdrawalCancel("出金取消"),
    
    Deposit("入金"),
    ;

    private final String desc;

    FrozenType(String desc) {
        this.desc = desc;
    }


}
