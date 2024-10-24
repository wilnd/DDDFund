package com.finpoints.bss.fund.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流水信息扩展表
 * @Author: Ryan Luo
 * @Date: 2021/4/29 11:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_flow_extend")
public class FlowExtend {

    /**
     * 流水表id
     */
    @TableId
    private String flowId;
    /**
     * 交易对方流水
     */
    private String tradeSerial;
    /**
     * 交易对方账号
     */
    private String tradeAccount;
    /**
     * 交易对方姓名
     */
    private String tradeAccountName;
    /**
     * 交易对方币种
     */
    private String tradeCurrency;
    /**
     * 备注2
     */
    private String remark;

}
