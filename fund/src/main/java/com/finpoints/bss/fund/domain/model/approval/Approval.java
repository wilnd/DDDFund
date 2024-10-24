package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.DomainEventPublisher;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Approval extends AggregateRoot {

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
    private ApprovalStatus status;

    /**
     * 审核人
     */
    private Operator operator;

    public Approval(ApprovalId approvalId, ApprovalType type, ApprovalRole role, String orderNo) {
        this.approvalId = approvalId;
        this.type = type;
        this.role = role;
        this.orderNo = orderNo;
        this.status = ApprovalStatus.Pending;

        DomainEventPublisher.instance()
                .publish(new ApprovalCreated(
                        this.approvalId,
                        this.type,
                        this.role,
                        this.orderNo,
                        this.status
                ));
    }

    /**
     * 审核通过
     *
     * @param requesterService 当前请求者服务
     */
    public void approve(CurrentRequesterService requesterService) {
        if (this.status != ApprovalStatus.Pending) {
            throw new IllegalStateException("Approval status is not pending");
        }
        if (!this.role.canOperate(requesterService.currentUserRole())) {
            throw new IllegalStateException("Current user role could not operate");
        }

        this.status = ApprovalStatus.Approved;
        this.operator = Operator.current();

        // 事件发布
        DomainEventPublisher.instance()
                .publish(new ApprovalApproved(
                        this.approvalId,
                        this.type,
                        this.role,
                        this.orderNo,
                        this.status
                ));
    }


    /**
     * 审核拒绝
     *
     * @param requesterService 当前请求者服务
     */
    public void reject(CurrentRequesterService requesterService) {
        if (this.status != ApprovalStatus.Pending) {
            throw new IllegalStateException("Approval status is not pending");
        }
        if (!this.role.canOperate(requesterService.currentUserRole())) {
            throw new IllegalStateException("Current user role could not operate");
        }

        this.status = ApprovalStatus.Rejected;
        this.operator = Operator.current();

        // 事件发布
        DomainEventPublisher.instance()
                .publish(new ApprovalRejected(
                        this.approvalId,
                        this.type,
                        this.role,
                        this.orderNo,
                        this.status
                ));
    }
}
