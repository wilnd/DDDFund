package com.finpoints.bss.fund.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finpoints.bss.fund.mybatis.entity.OrdersFlow;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersFlowMapper extends BaseMapper<OrdersFlow> {

}
