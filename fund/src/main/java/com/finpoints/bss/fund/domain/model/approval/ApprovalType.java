package com.finpoints.bss.fund.domain.model.approval;

import lombok.Getter;

/**
 * 审核单业务类型
 */
@Getter
public enum ApprovalType {

    Withdrawal("出金"),

    Deposit("入金"),

    ;
    private final String desc;

    ApprovalType(String desc) {
        this.desc = desc;
    }

}
