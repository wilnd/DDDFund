package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalId;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaApprovalRepository extends JpaRepository<JpaApproval, Long> {

    boolean existsByApprovalId(String approvalId);

    JpaApproval findByApprovalId(String approvalId);
    
    JpaApproval findByTypeAndRoleAndOrderNo(ApprovalType type, ApprovalRole role, String orderNo);
}
