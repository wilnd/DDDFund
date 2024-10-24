package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.CrudRepository;

public interface ApprovalRepository extends CrudRepository<Approval, ApprovalId> {

    Approval orderApproval(ApprovalType type, ApprovalRole role, String orderNo);
}
