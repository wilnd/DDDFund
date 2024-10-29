package com.finpoints.bss.fund.domain.model.approval;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import lombok.Getter;

@Getter
public class ApprovalCreated extends DomainEvent {

    /**
     * 审核单ID
     */
    private final ApprovalId approvalId;

    /**
     * 业务类型
     */
    private final ApprovalType type;

    /**
     * 审核角色
     */
    private final ApprovalRole role;

    /**
     * 关联业务订单号
     */
    private final String orderNo;

    /**
     * 审核状态
     */
    private final ApprovalStatus status;

    @JsonCreator
    public ApprovalCreated(ApprovalId approvalId, ApprovalType type, ApprovalRole role,
                           String orderNo, ApprovalStatus status) {
        this.approvalId = approvalId;
        this.type = type;
        this.role = role;
        this.orderNo = orderNo;
        this.status = status;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Approval;
    }
}
