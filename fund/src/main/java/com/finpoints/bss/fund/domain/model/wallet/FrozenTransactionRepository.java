package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.CrudRepository;

public interface FrozenTransactionRepository extends CrudRepository<FrozenTransaction, FrozenTransactionId> {

    /**
     * 幂等key获取冻结交易
     *
     * @param idemKey 幂等key
     * @return 冻结交易
     */
    FrozenTransaction findByIdemKey(String idemKey);

}
