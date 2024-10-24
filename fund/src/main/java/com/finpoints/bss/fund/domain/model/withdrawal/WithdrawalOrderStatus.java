package com.finpoints.bss.fund.domain.model.withdrawal;

public enum WithdrawalOrderStatus {

    CREATED("Created"),
    PENDING("Pending"),
    CANCELLED("Cancelled"),
    APPROVING("Approving"),
    REJECTED("Rejected"),
    ;

    private final String description;

    WithdrawalOrderStatus(String description) {
        this.description = description;
    }
}
