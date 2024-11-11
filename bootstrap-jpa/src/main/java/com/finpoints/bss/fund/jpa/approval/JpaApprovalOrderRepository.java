package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalBusinessType;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaApprovalOrderRepository extends JpaRepository<JpaApprovalOrder, Long> {

    boolean existsByOrderId(String approvalId);

    JpaApprovalOrder findByOrderId(String approvalId);

    JpaApprovalOrder findByRoleAndBusinessTypeAndBusinessOrderNo(ApprovalRole role,
                                                                 ApprovalBusinessType businessType,
                                                                 String businessOrderNo);

    List<JpaApprovalOrder> findByBusinessTypeAndBusinessOrderNo(ApprovalBusinessType businessType, String businessOrderNo);
}
