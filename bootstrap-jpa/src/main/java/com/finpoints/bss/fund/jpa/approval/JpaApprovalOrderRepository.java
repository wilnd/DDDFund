package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalBusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaApprovalOrderRepository extends JpaRepository<JpaApprovalOrder, Long> {

    boolean existsByOrderId(String approvalId);

    JpaApprovalOrder findByOrderId(String approvalId);

    JpaApprovalOrder findByTypeAndRoleAndBusinessOrderNo(ApprovalBusinessType type, ApprovalRole role, String businessOrderNo);
    
    List<JpaApprovalOrder> findByTypeAndBusinessOrderNo(ApprovalBusinessType type, String businessOrderNo);
}
