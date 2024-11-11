package com.finpoints.bss.fund.domain.model.common;

import lombok.Getter;

@Getter
public enum ResidentIDType {

    NATIONAL_ID("National ID"),
    PASSPORT("Passport"),
    OTHER("Other");

    private final String desc;

    ResidentIDType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
