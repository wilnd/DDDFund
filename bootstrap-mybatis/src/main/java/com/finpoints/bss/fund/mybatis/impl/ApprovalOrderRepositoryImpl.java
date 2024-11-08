package com.finpoints.bss.fund.mybatis.impl;

import com.finpoints.bss.fund.domain.model.approval.*;
import com.finpoints.bss.fund.mybatis.CrudRepositoryImpl;
import com.finpoints.bss.fund.mybatis.EntityConverter;
import com.finpoints.bss.fund.mybatis.entity.OrdersFlow;
import com.finpoints.bss.fund.mybatis.mapper.OrdersFlowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalOrderRepositoryImpl extends CrudRepositoryImpl<ApprovalOrder, ApprovalOrderId, OrdersFlow>
        implements ApprovalOrderRepository {

    private final OrdersFlowMapper ordersFlowMapper;

    public ApprovalOrderRepositoryImpl(OrdersFlowMapper ordersFlowMapper) {
        super(ordersFlowMapper, new ApprovalEntityConverter());
        this.ordersFlowMapper = ordersFlowMapper;
    }

    @Override
    protected String domainIdField() {
        return "";
    }

    @Override
    public ApprovalOrderId nextId() {
        return null;
    }

    @Override
    public ApprovalOrder orderApproval(ApprovalBusinessType type, ApprovalRole role, String orderNo) {
        return null;
    }

    @Override
    public List<ApprovalOrder> orderApprovals(ApprovalBusinessType type, String orderNo) {
        return List.of();
    }

    public static class ApprovalEntityConverter implements EntityConverter<ApprovalOrder, OrdersFlow> {

        @Override
        public ApprovalOrder toDomainEntity(OrdersFlow persistenceEntity) {
            return null;
        }

        @Override
        public OrdersFlow toPersistenceEntity(ApprovalOrder domainEntity) {
            return null;
        }
    }
}
