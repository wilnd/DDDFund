package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import lombok.Getter;

@Getter
public class ApprovalOrderRejected extends DomainEvent {

    private final ApprovalOrderId approvalOrderId;
    private final ApprovalType type;
    private final ApprovalRole role;
    private final String orderNo;
    private final ApprovalStatus status;
    private final String remark;

    public ApprovalOrderRejected(ApprovalOrderId approvalOrderId, ApprovalType type, ApprovalRole role,
                                 String orderNo, ApprovalStatus status, String remark) {
        this.approvalOrderId = approvalOrderId;
        this.type = type;
        this.role = role;
        this.orderNo = orderNo;
        this.status = status;
        this.remark = remark;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Approval;
    }
}
