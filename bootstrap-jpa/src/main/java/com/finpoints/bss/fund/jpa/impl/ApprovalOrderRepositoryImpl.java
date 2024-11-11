package com.finpoints.bss.fund.jpa.impl;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.approval.*;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.approval.JpaApprovalOrder;
import com.finpoints.bss.fund.jpa.approval.JpaApprovalOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalOrderRepositoryImpl extends CrudRepositoryImpl<ApprovalOrder, ApprovalOrderId, JpaApprovalOrder>
        implements ApprovalOrderRepository {

    private final JpaApprovalOrderRepository jpaApprovalOrderRepository;

    public ApprovalOrderRepositoryImpl(JpaApprovalOrderRepository jpaApprovalOrderRepository) {
        super(new ApprovalEntityConverter(), jpaApprovalOrderRepository);
        this.jpaApprovalOrderRepository = jpaApprovalOrderRepository;
    }

    @Override
    public ApprovalOrderId nextId() {
        return new ApprovalOrderId(IdentityGenerator.nextIdentity());
    }

    @Override
    public ApprovalOrder findById(ApprovalOrderId id) {
        JpaApprovalOrder approval = jpaApprovalOrderRepository.findByOrderId(id.rawId());
        return convertToDomain(approval);
    }

    @Override
    public boolean existsById(ApprovalOrderId id) {
        return jpaApprovalOrderRepository.existsByOrderId(id.rawId());
    }

    @Override
    public ApprovalOrder orderApproval(ApprovalBusinessType type, ApprovalRole role, String orderNo) {
        JpaApprovalOrder approval = jpaApprovalOrderRepository.findByRoleAndBusinessTypeAndBusinessOrderNo(role, type, orderNo);
        return convertToDomain(approval);
    }

    @Override
    public List<ApprovalOrder> orderApprovals(ApprovalBusinessType type, String orderNo) {
        return jpaApprovalOrderRepository.findByBusinessTypeAndBusinessOrderNo(type, orderNo).stream()
                .map(this::convertToDomain)
                .toList();
    }

    public static class ApprovalEntityConverter implements JpaEntityConverter<ApprovalOrder, JpaApprovalOrder> {

        @Override
        public ApprovalOrder toDomainEntity(JpaApprovalOrder persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            return new ApprovalOrder(
                    new ApprovalOrderId(persistenceEntity.getOrderId()),
                    persistenceEntity.getRole(),
                    persistenceEntity.getBusinessType(),
                    persistenceEntity.getBusinessOrderNo(),
                    persistenceEntity.getStatus(),
                    persistenceEntity.getApprovalTime(),
                    persistenceEntity.getRemark(),
                    persistenceEntity.getOperator()
            );
        }

        @Override
        public JpaApprovalOrder toPersistenceEntity(ApprovalOrder domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            return new JpaApprovalOrder(
                    domainEntity.getOrderId().rawId(),
                    domainEntity.getRole(),
                    domainEntity.getBusinessType(),
                    domainEntity.getBusinessOrderNo(),
                    domainEntity.getStatus(),
                    domainEntity.getApprovalTime(),
                    domainEntity.getRemark(),
                    domainEntity.getOperator()
            );
        }
    }


}
