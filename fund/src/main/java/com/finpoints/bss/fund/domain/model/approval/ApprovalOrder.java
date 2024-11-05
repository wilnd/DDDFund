package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.DomainEventPublisher;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ApprovalOrder extends AggregateRoot {

    /**
     * 审核单ID
     */
    private final ApprovalOrderId orderId;

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
    private final String businessOrderNo;

    /**
     * 审核状态
     */
    private ApprovalStatus status;

    /**
     * 审核时间
     */
    private Instant approvalTime;

    /**
     * 审核备注
     */
    private String remark;

    /**
     * 审核人
     */
    private Operator operator;

    public ApprovalOrder(ApprovalOrderId orderId, ApprovalType type, ApprovalRole role, String businessOrderNo) {
        this.orderId = orderId;
        this.type = type;
        this.role = role;
        this.businessOrderNo = businessOrderNo;
        this.status = ApprovalStatus.Pending;

        DomainEventPublisher.instance()
                .publish(new ApprovalOrderCreated(
                        this.orderId,
                        this.type,
                        this.role,
                        this.businessOrderNo,
                        this.status
                ));
    }

    /**
     * 审核通过
     *
     * @param requesterService 当前请求者服务
     */
    public void approve(CurrentRequesterService requesterService, String remark) {
        if (this.status != ApprovalStatus.Pending) {
            throw new IllegalStateException("Approval status is not pending");
        }
        if (!this.role.canOperate(requesterService.currentUserRole())) {
            throw new IllegalStateException("Current user role could not operate");
        }

        this.remark = remark;
        this.status = ApprovalStatus.Approved;
        this.approvalTime = Instant.now();
        this.operator = Operator.current();

        // 事件发布
        DomainEventPublisher.instance()
                .publish(new ApprovalOrderApproved(
                        this.orderId,
                        this.type,
                        this.role,
                        this.businessOrderNo,
                        this.status,
                        this.remark
                ));
    }

    /**
     * 审核拒绝
     *
     * @param requesterService 当前请求者服务
     */
    public void reject(CurrentRequesterService requesterService, String remark) {
        if (this.status != ApprovalStatus.Pending) {
            throw new IllegalStateException("Approval status is not pending");
        }
        if (!this.role.canOperate(requesterService.currentUserRole())) {
            throw new IllegalStateException("Current user role could not operate");
        }

        this.remark = remark;
        this.status = ApprovalStatus.Rejected;
        this.approvalTime = Instant.now();
        this.operator = Operator.current();

        // 事件发布
        DomainEventPublisher.instance()
                .publish(new ApprovalOrderRejected(
                        this.orderId,
                        this.type,
                        this.role,
                        this.businessOrderNo,
                        this.status,
                        this.remark
                ));
    }

    /**
     * 是否可以撤销
     */
    public boolean canCancel() {
        return this.status == ApprovalStatus.Pending;
    }

    /**
     * 审核撤销
     */
    public void cancel() {
        if (this.status != ApprovalStatus.Pending) {
            throw new IllegalStateException("Approval status is not pending");
        }

        this.status = ApprovalStatus.Cancelled;
        this.operator = Operator.current();
    }
}
