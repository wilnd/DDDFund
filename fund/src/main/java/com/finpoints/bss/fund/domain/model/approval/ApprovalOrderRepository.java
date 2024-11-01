package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.CrudRepository;

public interface ApprovalOrderRepository extends CrudRepository<ApprovalOrder, ApprovalOrderId> {

    ApprovalOrder orderApproval(ApprovalType type, ApprovalRole role, String orderNo);
}
