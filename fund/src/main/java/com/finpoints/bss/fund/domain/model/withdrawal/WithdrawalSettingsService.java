package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.common.UserId;

public interface WithdrawalSettingsService {

    /**
     * 获取用户出金配置
     *
     * @param userId 用户ID
     * @return 出金配置
     */
    WithdrawalSettings getUserSetting(UserId userId);
}
