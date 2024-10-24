package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.approval.*;
import com.finpoints.bss.fund.jpa.AbstractRepository;
import com.finpoints.bss.fund.jpa.EntityConverter;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalRepositoryImpl extends AbstractRepository<Approval, ApprovalId, JpaApproval, Long>
        implements ApprovalRepository {

    private final JpaApprovalRepository jpaApprovalRepository;

    public ApprovalRepositoryImpl(JpaApprovalRepository jpaApprovalRepository) {
        super(new ApprovalEntityConverter(), jpaApprovalRepository);
        this.jpaApprovalRepository = jpaApprovalRepository;
    }

    @Override
    public ApprovalId nextId() {
        return new ApprovalId(IdentityGenerator.nextIdentity());
    }

    @Override
    public Approval findById(ApprovalId id) {
        JpaApproval approval = jpaApprovalRepository.findByApprovalId(id.rawId());
        return convertToDomain(approval);
    }

    @Override
    public boolean existsById(ApprovalId id) {
        return jpaApprovalRepository.existsByApprovalId(id.rawId());
    }

    @Override
    public Approval orderApproval(ApprovalType type, ApprovalRole role, String orderNo) {
        JpaApproval approval = jpaApprovalRepository.findByTypeAndRoleAndOrderNo(type, role, orderNo);
        return convertToDomain(approval);
    }

    public static class ApprovalEntityConverter implements EntityConverter<Approval, JpaApproval> {

        @Override
        public Approval toDomainEntity(JpaApproval persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new Approval(
                    new ApprovalId(persistenceEntity.getApprovalId()),
                    persistenceEntity.getType(),
                    persistenceEntity.getRole(),
                    persistenceEntity.getOrderNo(),
                    persistenceEntity.getStatus(),
                    persistenceEntity.getOperator()
            );
        }

        @Override
        public JpaApproval toPersistenceEntity(Approval domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaApproval(
                    domainEntity.getApprovalId().rawId(),
                    domainEntity.getType(),
                    domainEntity.getRole(),
                    domainEntity.getOrderNo(),
                    domainEntity.getStatus(),
                    domainEntity.getOperator()
            );
        }
    }


}
