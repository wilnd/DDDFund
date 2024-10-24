package com.finpoints.bss.fund.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author stick wang
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("api_wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String appid;

    private String uuid;

    /**
     * 账户货币
     */
    private String currency;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 可提余额
     */
    private BigDecimal drawableBalance;

    private Timestamp createTime;

    private Timestamp modifyTime;

    /**
     * 是否为主钱包，默认否
     */
    private Integer isDefault;

    /**
     * 钱包用户类型
     */
    private String utype;

    /**
     * 钱包类型。分佣金钱包和交易钱包
     */
    private String walletType;

    private Integer isDeleted;

    private Boolean digital;

    /**
     * bss-127 需求增加手工冻结金额
     */
    private BigDecimal manualFrozenBalance;

}
