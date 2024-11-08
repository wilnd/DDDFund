package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import lombok.Getter;

@Getter
public class ApprovalOrderRejected extends DomainEvent {

    private final ApprovalOrderId orderId;
    private final ApprovalRole role;
    private final ApprovalBusinessType businessType;
    private final String businessOrderNo;
    private final ApprovalStatus status;
    private final String remark;

    public ApprovalOrderRejected(String appId, ApprovalOrderId orderId, ApprovalRole role,
                                 ApprovalBusinessType businessType, String businessOrderNo,
                                 ApprovalStatus status, String remark) {
        super(appId);
        this.orderId = orderId;
        this.businessType = businessType;
        this.role = role;
        this.businessOrderNo = businessOrderNo;
        this.status = status;
        this.remark = remark;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Approval;
    }
}
