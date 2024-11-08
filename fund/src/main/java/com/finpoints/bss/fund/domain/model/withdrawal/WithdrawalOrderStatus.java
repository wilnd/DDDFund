package com.finpoints.bss.fund.domain.model.withdrawal;

public enum WithdrawalOrderStatus {

    CREATED("Created"),
    PENDING_APPROVAL("Pending Approval"),
    CANCELLED("Cancelled"),
    APPROVING("Approving"),
    REJECTED("Rejected"),
    PENDING_REMITTANCE("Pending Remittance"),
    REMITTING("Remitting"),

    SUCCESSFUL("Successful"),
    FAILED("Failed"),
    RECALLED("Recalled"),
    ;

    private final String description;

    WithdrawalOrderStatus(String description) {
        this.description = description;
    }
}
