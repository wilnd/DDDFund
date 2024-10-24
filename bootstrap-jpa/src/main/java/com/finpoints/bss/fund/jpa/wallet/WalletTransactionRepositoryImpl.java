package com.finpoints.bss.fund.jpa.wallet;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.wallet.WalletTransaction;
import com.finpoints.bss.fund.domain.model.wallet.WalletTransactionId;
import com.finpoints.bss.fund.domain.model.wallet.WalletTransactionRepository;
import com.finpoints.bss.fund.jpa.AbstractRepository;
import com.finpoints.bss.fund.jpa.EntityConverter;
import org.springframework.stereotype.Repository;

@Repository
public class WalletTransactionRepositoryImpl extends AbstractRepository<WalletTransaction, WalletTransactionId, JpaWalletTransaction, Long>
        implements WalletTransactionRepository {

    private final JpaWalletTransactionRepository jpaWalletTransactionRepository;

    public WalletTransactionRepositoryImpl(JpaWalletTransactionRepository jpaWalletTransactionRepository) {
        super(new JpaWalletTransactionEntityConverter(), jpaWalletTransactionRepository);
        this.jpaWalletTransactionRepository = jpaWalletTransactionRepository;
    }

    @Override
    public WalletTransactionId nextId() {
        return new WalletTransactionId(IdentityGenerator.nextIdentity());
    }

    @Override
    public WalletTransaction findById(WalletTransactionId id) {
        return null;
    }

    @Override
    public boolean existsById(WalletTransactionId id) {
        return false;
    }


    public static class JpaWalletTransactionEntityConverter implements EntityConverter<WalletTransaction, JpaWalletTransaction> {

        @Override
        public WalletTransaction toDomainEntity(JpaWalletTransaction persistenceEntity) {
            return null;
        }

        @Override
        public JpaWalletTransaction toPersistenceEntity(WalletTransaction domainEntity) {
            return null;
        }
    }

}
