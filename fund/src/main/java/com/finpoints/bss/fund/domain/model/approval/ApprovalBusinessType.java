package com.finpoints.bss.fund.domain.model.approval;

import lombok.Getter;

/**
 * 审核单业务类型
 */
@Getter
public enum ApprovalBusinessType {

    WITHDRAWAL("出金"),
    DEPOSIT("入金"),

    ;
    private final String desc;

    ApprovalBusinessType(String desc) {
        this.desc = desc;
    }

}
