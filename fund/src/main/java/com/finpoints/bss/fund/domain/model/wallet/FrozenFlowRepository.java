package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.CrudRepository;

public interface FrozenFlowRepository extends CrudRepository<FrozenFlow, FrozenFlowId> {

    /**
     * 通过业务订单号获取冻结交易
     *
     * @param walletId   钱包ID
     * @param freezeType 冻结类型
     * @param orderNo    业务订单号
     * @return 冻结交易
     */
    FrozenFlow orderFlow(WalletId walletId, FrozenType freezeType, String orderNo);

}
