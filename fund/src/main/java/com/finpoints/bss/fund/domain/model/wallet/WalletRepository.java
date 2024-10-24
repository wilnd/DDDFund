package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, WalletId> {

    /**
     * Find wallet by wallet id and wallet type
     *
     * @param walletId   Wallet id
     * @param walletType Wallet type
     * @return Wallet
     */
    Wallet walletOf(WalletId walletId, WalletType walletType);
}
