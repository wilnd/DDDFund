package com.finpoints.bss.fund.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderRepository;
import com.finpoints.bss.fund.mybatis.CrudRepositoryImpl;
import com.finpoints.bss.fund.mybatis.EntityConverter;
import com.finpoints.bss.fund.mybatis.entity.OrdersFlow;
import com.finpoints.bss.fund.mybatis.mapper.OrdersFlowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WithdrawalOrderRepositoryImpl extends CrudRepositoryImpl<WithdrawalOrder, WithdrawalOrderNo, OrdersFlow>
        implements WithdrawalOrderRepository {

    private final OrdersFlowMapper ordersFlowMapper;

    public WithdrawalOrderRepositoryImpl(OrdersFlowMapper ordersFlowMapper) {
        super(ordersFlowMapper, new WithdrawalOrderEntityConverter());
        this.ordersFlowMapper = ordersFlowMapper;
    }

    @Override
    public WithdrawalOrderNo nextId() {
        return new WithdrawalOrderNo(IdentityGenerator.nextIdentity());
    }

    @Override
    protected String domainIdField() {
        return "serial";
    }

    @Override
    public WithdrawalOrder userWithdrawalOrder(UserId userId, WithdrawalOrderNo orderNo) {
        QueryWrapper<OrdersFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", userId.rawId())
                .eq("serial", orderNo.rawId());

        OrdersFlow ordersFlow = ordersFlowMapper.selectOne(queryWrapper);
        return convertToDomain(ordersFlow);
    }


    public static class WithdrawalOrderEntityConverter implements EntityConverter<WithdrawalOrder, OrdersFlow> {

        @Override
        public WithdrawalOrder toDomainEntity(OrdersFlow persistenceEntity) {
            if (persistenceEntity == null) {
                return null;
            }
            // TODO: Convert OrdersFlow to WithdrawalOrder
            return null;
        }

        @Override
        public OrdersFlow toPersistenceEntity(WithdrawalOrder domainEntity) {
            if (domainEntity == null) {
                return null;
            }
            // TODO: Convert WithdrawalOrder to OrdersFlow
            return null;
        }
    }
}
