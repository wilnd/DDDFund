package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaWalletRepository extends JpaRepository<JpaWallet, Long> {

    boolean existsByWalletId(String walletId);

    JpaWallet findByWalletId(String walletId);

    JpaWallet findByWalletIdAndType(String walletId, WalletType type);
}
