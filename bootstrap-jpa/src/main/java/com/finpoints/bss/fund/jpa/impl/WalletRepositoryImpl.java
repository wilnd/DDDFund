package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.Wallet;
import com.finpoints.bss.fund.domain.model.wallet.WalletId;
import com.finpoints.bss.fund.domain.model.wallet.WalletRepository;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.wallet.JpaWallet;
import com.finpoints.bss.fund.jpa.wallet.JpaWalletRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletRepositoryImpl extends CrudRepositoryImpl<Wallet, WalletId, JpaWallet>
        implements WalletRepository {

    private final JpaWalletRepository jpaWalletRepository;

    public WalletRepositoryImpl(JpaWalletRepository jpaWalletRepository) {
        super(new WalletEntityConverter(), jpaWalletRepository);
        this.jpaWalletRepository = jpaWalletRepository;
    }

    @Override
    public WalletId nextId() {
        return new WalletId(IdentityGenerator.nextIdentity());
    }

    @Override
    public Wallet findById(WalletId id) {
        JpaWallet jpaWallet = jpaWalletRepository.findByWalletId(id.rawId());
        return convertToDomain(jpaWallet);
    }

    @Override
    public boolean existsById(WalletId id) {
        return jpaWalletRepository.existsByWalletId(id.rawId());
    }

    @Override
    public Wallet walletOf(WalletId walletId, WalletType walletType) {
        JpaWallet jpaWallet = jpaWalletRepository.findByWalletIdAndType(walletId.rawId(), walletType);
        return convertToDomain(jpaWallet);
    }

    public static class WalletEntityConverter implements JpaEntityConverter<Wallet, JpaWallet> {
        @Override
        public Wallet toDomainEntity(JpaWallet persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }

            return new Wallet(
                    new WalletId(persistenceEntity.getWalletId()),
                    persistenceEntity.getUserRole(),
                    new UserId(persistenceEntity.getUserId()),
                    persistenceEntity.getCurrency(),
                    persistenceEntity.getType(),
                    persistenceEntity.getMainWallet(),
                    persistenceEntity.getAvailableBalance(),
                    persistenceEntity.getFrozenBalance(),
                    persistenceEntity.getAvailableBalance(),
                    persistenceEntity.getDrawableBalance()
            );
        }

        @Override
        public JpaWallet toPersistenceEntity(Wallet domainEntity) {
            if (domainEntity == null) {
                return null;
            }

            return new JpaWallet(
                    domainEntity.getWalletId().rawId(),
                    domainEntity.getUserRole(),
                    domainEntity.getUserId().rawId(),
                    domainEntity.getCurrency(),
                    domainEntity.getType(),
                    domainEntity.getMainWallet(),
                    domainEntity.getAvailableBalance(),
                    domainEntity.getFrozenBalance(),
                    domainEntity.getAvailableBalance(),
                    domainEntity.getDrawableBalance()
            );
        }
    }
}
