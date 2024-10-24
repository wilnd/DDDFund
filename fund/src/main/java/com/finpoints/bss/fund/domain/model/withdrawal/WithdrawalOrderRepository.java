package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.CrudRepository;
import com.finpoints.bss.fund.domain.model.common.UserId;

public interface WithdrawalOrderRepository extends CrudRepository<WithdrawalOrder, WithdrawalOrderNo> {

    /**
     * 获取用户出金订单
     *
     * @param userId  用户ID
     * @param orderNo 订单号
     * @return 出金订单
     */
    WithdrawalOrder userWithdrawalOrder(UserId userId, WithdrawalOrderNo orderNo);
}
