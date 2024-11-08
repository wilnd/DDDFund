package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import lombok.Getter;

@Getter
public class ApprovalOrderCreated extends DomainEvent {

    /**
     * 审核单ID
     */
    private final ApprovalOrderId orderId;

    /**
     * 审核角色
     */
    private final ApprovalRole role;

    /**
     * 业务类型
     */
    private final ApprovalBusinessType businessType;

    /**
     * 关联业务订单号
     */
    private final String businessOrderNo;

    /**
     * 审核状态
     */
    private final ApprovalStatus status;

    public ApprovalOrderCreated(String appId, ApprovalOrderId orderId, ApprovalRole role,
                                ApprovalBusinessType businessType, String businessOrderNo,
                                ApprovalStatus status) {
        super(appId);
        this.orderId = orderId;
        this.businessType = businessType;
        this.role = role;
        this.businessOrderNo = businessOrderNo;
        this.status = status;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.Approval;
    }
}
