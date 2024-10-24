package com.finpoints.bss.fund.domain.model.wallet;

import lombok.Getter;

@Getter
public enum FrozenStatus {

    FROZEN("冻结"),
    UNFROZEN("解冻");

    private final String desc;

    FrozenStatus(String desc) {
        this.desc = desc;
    }
}
