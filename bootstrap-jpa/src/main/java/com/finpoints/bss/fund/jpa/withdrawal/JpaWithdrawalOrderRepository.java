package com.finpoints.bss.fund.jpa.withdrawal;

import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWithdrawalOrderRepository extends JpaRepository<JpaWithdrawalOrder, Long> {

    boolean existsByOrderNo(String orderNo);

    JpaWithdrawalOrder findByOrderNo(String orderNo);

    JpaWithdrawalOrder findByOrderNoAndUserId(String orderNo, String userId);
}
