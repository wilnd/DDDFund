package com.finpoints.bss.fund.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.fund.mybatis.EntityAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


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
@TableName(value = "api_orders_flow", autoResultMap = true)
public class OrdersFlow implements EntityAdapter, Serializable {

    private static final long serialVersionUID = 6711402180748846658L;
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * appid
     */
    private String appid;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 订单号
     */
    private String serial;

    /**
     * 交易单号
     */
    private String transactionId;

    private String company;

    /**
     * 入金凭证
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<OrderDepositVoucher> depositVoucher;

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
     * 汇率
     */
    private BigDecimal rate;

    /**
     * 原货币币种
     */
    private String originalCurrency;

    /**
     * 目标货币币种
     */
    private String targetCurrency;

    /**
     * 原有余额
     */
    private BigDecimal originalBalance;

    /**
     * 余额变动
     */
    private BigDecimal balanceChange;

    /**
     * 发起时间
     */
    private Timestamp requestTime;

    /**
     * 审核时间
     */
    private Timestamp auditTime;

    /**
     * 入账时间
     */
    private Timestamp payTime;

    /**
     * 0：未支付，1：已支付
     */
    private Integer payed;

    /**
     * 审核状态
     */
    @TableField("`status`")
    private String status;

    /**
     * 到账金额
     */
    private BigDecimal arrivalAmount;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行备注
     */
    private String bankRemark;

    /**
     * 转入账号
     */
    private String transferAccount;

    /**
     * 发卡地区
     */
    private String hairpinArea;

    /**
     * SWIFT代码
     */
    private String swiftCode;

    /**
     * IBAN代码
     */
    private String ibanCode;

    /**
     * 转出账号
     */
    private String accountOut;

    /**
     * 支行名称
     */
    private String branchName;

    /**
     * 转出资金
     */
    private BigDecimal capitaOut;

    /**
     * 开户名称
     */
    private String userName;

    /**
     * 银行地址
     */
    private String bankAddress;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 收款地址
     */
    private String receivableAddress;

    /**
     * 备注
     */
    private String remark;

    /**
     * 现有余额
     */
    private BigDecimal existingBalance;

    /**
     * MT订单号
     */
    private String mtSerial;

    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 手续费
     */
    private BigDecimal serviceCharge;

    /**
     * 出金金额
     */
    private BigDecimal withdrawalAmount;

    /**
     * 入金金额
     */
    private BigDecimal depositAmount;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 额外的信息JSON
     */
    private String extra;

    private String channelId;

    private String bankcode;

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
     * 入金账号
     */
    private String depositAccount;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 出金账号
     */
    private String withdrawalAccount;

    /**
     * 钱包ID
     */
    private String walletId;

    /**
     * 内部转入钱包id
     */
    private String inWalletId;

    /**
     * 是否删除（默认未删除，1：删除）
     */
    private Integer isDeleted;

    /**
     * 账号
     */
    private Integer login;

    /**
     * 服务器id
     */
    private String serverId;

    /**
     * 账户类型（client或sales）
     */
    private String utype;

    /**
     * 出金回撤时间
     */
    private Timestamp recallTime;

    private String agents;

    private Integer sync;

    /**
     * 数字货币相关数据
     */
    private String sales;

    /**
     * 数字货币钱包地址
     */
    private String dcData;

    /**
     * 中转银行名称(国际电汇)
     */
    private String intermediaryBankName;

    /**
     * 中转银行地址(国际电汇)
     */
    private String intermediaryBankAddress;

    /**
     * 中转银行SWIFT(国际电汇)
     */
    private String intermediaryBankSwift;

    /**
     * 中转银行账号(国际电汇)
     */
    private String intermediaryBankAccount;

    /**
     * 中转银行备注(国际电汇)
     */
    private String intermediaryBankRemark;

    private String bankId;

    private String timetable;

    private Timestamp operateTime;

    private BigDecimal originalServiceCharge;

    private String country;

    private String auditRemark;

    private String isFree;

    private Integer isNew;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 发起人角色
     */
    private String operatorRole;

    /**
     * 操作方式id
     */
    private Integer operationModeId;

    /**
     * 一级分类
     */
    @TableField(exist = false)
    private String oneLevelClassification;

    /**
     * 二级分类
     */
    @TableField(exist = false)
    private String twoLevelClassification;

    /**
     * 三级分类
     */
    @TableField(exist = false)
    private String threeLevelClassification;

    /**
     * 清算标识
     * BSSCODE
     */
    private String clearingMark;

    @Override
    public Serializable id() {
        return this.id;
    }

    @Override
    public boolean softDelete() {
        this.isDeleted = 1;
        return true;
    }

    @Override
    public void copyFrom(Entity entity) {

    }

    @Override
    public void copyTo(Entity entity) {

    }
}
