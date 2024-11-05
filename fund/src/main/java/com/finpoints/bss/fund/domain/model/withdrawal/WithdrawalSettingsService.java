package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.common.UserRole;

public interface WithdrawalSettingsService {

    /**
     * 获取用户出金配置
     *
     * @param userId 用户ID
     * @return 出金配置
     */
    WithdrawalSettings getUserSetting(UserId userId);

    /**
     * 获取角色出金配置
     *
     * @param userRole 用户角色
     * @return 出金配置
     */
    WithdrawalSettings getRoleSetting(UserRole userRole);
}
