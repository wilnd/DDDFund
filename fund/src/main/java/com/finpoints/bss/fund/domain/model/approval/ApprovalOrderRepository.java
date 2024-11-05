package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.CrudRepository;

import java.util.List;

public interface ApprovalOrderRepository extends CrudRepository<ApprovalOrder, ApprovalOrderId> {

    /**
     * 获取订单的一个审核单
     *
     * @param type    审核类型
     * @param role    审核角色
     * @param orderNo 订单号
     * @return 审核单
     */
    ApprovalOrder orderApproval(ApprovalType type, ApprovalRole role, String orderNo);

    /**
     * 获取订单的所有审核单
     *
     * @param type    审核类型
     * @param orderNo 订单号
     * @return 审核单列表
     */
    List<ApprovalOrder> orderApprovals(ApprovalType type, String orderNo);
}
