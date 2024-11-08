package com.finpoints.bss.fund.domain.model.approval;

import lombok.Getter;

@Getter
public enum ApprovalStatus {

    PENDING("Pending"),
    PASSED("Passed"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled"),
    ;

    private final String desc;

    ApprovalStatus(String desc) {
        this.desc = desc;
    }
}
