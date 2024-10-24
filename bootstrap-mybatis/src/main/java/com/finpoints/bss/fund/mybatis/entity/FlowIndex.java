package com.finpoints.bss.fund.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_flow_index")
public class FlowIndex implements Serializable {
    private static final long serialVersionUID = 5875845369023857710L;


    private int id;

    /**
     * 流水号ID
     */
    private String flowId;

    private String appid;

    private String uuid;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作方式
     */
    private String operationMode;

    /**
     * 资金流向
     */
    private String capitalFlow;

    /**
     * 货币币种
     */
    private String currency;

    /**
     * 原有余额
     */
    private BigDecimal originalBalance;

    /**
     * 手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 余额变动
     */
    private BigDecimal balanceChange;

    /**
     * 现有余额
     */
    private BigDecimal existingBalance;

    /**
     * 订单编号
     */
    private String orderSerial;

    /**
     * 备注
     */
    private String remark;

    /**
     * 钱包ID
     */
    private String walletId;

    /**
     * 钱包类型
     */
    private String walletType;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 账户类型（client或sales）
     */
    private String utype;

    /**
     * 是否删除（默认0未删除，1：删除）
     */
    private Integer isDeleted;

    private Integer sync;

    /**
     * 毫秒级时间戳
     */
    private Long microTime;

    private String agents;

    private String sales;

    /**
     * 三方支付渠道
     */
    private String channelCode;

    /**
     * 审核时汇率
     */
    private BigDecimal auditRate;

    /**
     * usd手续费
     */
    private BigDecimal usdServiceCharge;

    /**
     * usd金额
     */
    private BigDecimal usdBalanceChange;

    /**
     * 校验参数
     */
    private String sign;
}
