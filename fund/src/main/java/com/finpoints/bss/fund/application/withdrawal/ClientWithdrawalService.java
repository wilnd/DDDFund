package com.finpoints.bss.fund.application.withdrawal;

import com.finpoints.bss.fund.application.withdrawal.command.ApplyWithdrawalCommand;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;

public interface ClientWithdrawalService {

    /**
     * 申请出金
     *
     * @param command 申请出金命令
     * @return 出金订单号
     */
    WithdrawalOrderNo applyWithdrawal(ApplyWithdrawalCommand command);

    /**
     * 取消出金
     *
     * @param userId            用户ID
     * @param withdrawalOrderNo 出金订单号
     * @return 出金订单
     */
    WithdrawalOrder cancelWithdrawal(String userId, String withdrawalOrderNo);
}
