package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaApprovalOrderRepository extends JpaRepository<JpaApprovalOrder, Long> {

    boolean existsByOrderId(String approvalId);

    JpaApprovalOrder findByOrderId(String approvalId);

    JpaApprovalOrder findByTypeAndRoleAndBusinessOrderNo(ApprovalType type, ApprovalRole role, String businessOrderNo);
}
