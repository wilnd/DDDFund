package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.DomainEventPublisher;
import com.finpoints.bss.fund.domain.model.common.BankId;
import com.finpoints.bss.fund.domain.model.common.BankInfo;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.mt.MtRequestId;
import com.finpoints.bss.fund.domain.model.wallet.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class WithdrawalOrder extends AggregateRoot {

    /**
     * 订单号
     */
    private final WithdrawalOrderNo orderNo;

    /**
     * 用户ID
     */
    private final UserId userId;

    /**
     * 出金钱包
     */
    private final WalletId walletId;

    /**
     * 出金钱包类型
     */
    private final WalletType walletType;

    /**
     * 出金方式
     */
    private final WithdrawalMethod withdrawalMethod;

    /**
     * (客户端)请求时间
     */
    private final Instant requestTime;

    /**
     * 汇率
     */
    private final BigDecimal exchangeRate;

    /**
     * 出金原始币种
     */
    private final Currency originalCurrency;

    /**
     * 出金目标币种
     */
    private final Currency targetCurrency;

    /**
     * 出金金额
     */
    private BigDecimal amount;

    /**
     * 到账金额
     */
    private BigDecimal arrivalAmount;

    /**
     * 服务费
     */
    private BigDecimal serviceCharge;

    /**
     * (银行出金)银行ID
     */
    private BankId bankId;

    /**
     * (银行出金)银行账号
     */
    private String bankAccount;

    /**
     * (银行出金)银行信息
     */
    private BankInfo bankInfo;

    /**
     * (国际电汇)中转银行ID
     */
    private BankId intermediaryBankId;

    /**
     * (国际电汇)中转银行账号
     */
    private String intermediaryBankAccount;

    /**
     * (国际电汇)中转银行信息
     */
    private BankInfo intermediaryBankInfo;

    /**
     * 订单状态
     */
    private WithdrawalOrderStatus status;

    /**
     * MT出金请求ID，仅在出金订单为MT出金时有值
     */
    private MtRequestId mtRequestId;

    /**
     * 冻结流水ID
     */
    private FrozenFlowId frozenFlowId;

    /**
     * 出金回撤时间
     */
    private Instant recallTime;

    WithdrawalOrder(String appId, WithdrawalOrderNo orderNo, UserId userId, WalletId walletId, WalletType walletType,
                    WithdrawalMethod withdrawalMethod, Instant requestTime, BigDecimal exchangeRate,
                    Currency originalCurrency, Currency targetCurrency, BigDecimal amount, BigDecimal arrivalAmount,
                    BigDecimal serviceCharge, String bankAccount, BankId bankId, BankInfo bankInfo,
                    BankId intermediaryBankId, String intermediaryBankAccount, BankInfo intermediaryBankInfo,
                    MtRequestId mtRequestId) {
        super(appId);
        this.orderNo = orderNo;
        this.userId = userId;
        this.walletId = walletId;
        this.walletType = walletType;
        this.withdrawalMethod = withdrawalMethod;
        this.requestTime = requestTime;
        this.exchangeRate = exchangeRate;
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.arrivalAmount = arrivalAmount;
        this.serviceCharge = serviceCharge;
        this.bankAccount = bankAccount;
        this.bankId = bankId;
        this.bankInfo = bankInfo;
        this.intermediaryBankId = intermediaryBankId;
        this.intermediaryBankAccount = intermediaryBankAccount;
        this.intermediaryBankInfo = intermediaryBankInfo;
        this.mtRequestId = mtRequestId;

        this.status = WithdrawalOrderStatus.CREATED;
    }

    public static WithdrawalOrder of() {
        return null;
    }

    /**
     * 创建出金订单
     */
    public static WithdrawalOrder ofBank(String appId, WithdrawalOrderNo orderNo, UserId userId,
                                         WalletId walletId, WalletType walletType,
                                         Instant requestTime, BigDecimal exchangeRate,
                                         Currency originalCurrency, Currency targetCurrency,
                                         BigDecimal amount, BigDecimal arrivalAmount,
                                         BankId bankId, String bankAccount, BankInfo bankInfo,
                                         MtRequestId mtRequestId) {
        return new WithdrawalOrder(appId, orderNo, userId, walletId, walletType, WithdrawalMethod.BANK,
                requestTime, exchangeRate, originalCurrency, targetCurrency, amount, arrivalAmount,
                BigDecimal.ZERO, bankAccount, bankId, bankInfo,
                null, null, null,
                mtRequestId);
    }

    public static WithdrawalOrder ofWire() {
        return null;
    }

    /**
     * 提交出金订单
     * 提交出金订单后，订单状态变为待审核
     */
    public void submit(WalletOperationService walletOperationService) {
        Validate.isTrue(this.status == WithdrawalOrderStatus.CREATED, "Expected status is CREATED");

        // 冻结出金金额
        this.frozenFlowId = walletOperationService.freezeWalletAmount(
                this.getWalletId(),
                FrozenType.WITHDRAWAL, amount,
                this.orderNo.rawId(),
                "");
        this.updateStatus(WithdrawalOrderStatus.PENDING_APPROVAL);

        // 发布出金订单提交事件
        DomainEventPublisher.instance()
                .publish(new WithdrawalOrderSubmitted(
                        this.getAppId(),
                        orderNo, userId,
                        walletId, walletType,
                        withdrawalMethod,
                        exchangeRate,
                        amount,
                        status
                ));
    }

    /**
     * 取消出金订单
     */
    public void cancel(WithdrawalOrderStatus status, WalletOperationService walletOperationService) {
        Validate.isTrue(this.status == WithdrawalOrderStatus.PENDING_APPROVAL || this.status == WithdrawalOrderStatus.APPROVING,
                "Expected status is PENDING or APPROVING");

        // 解冻出金金额
        walletOperationService.unfreezeWalletAmount(
                walletId, frozenFlowId,
                FrozenType.WITHDRAWAL_CANCEL, amount,
                "");
        this.updateStatus(status);

        // 发布出金订单取消事件
        DomainEventPublisher.instance()
                .publish(new WithdrawalOrderCancelled(
                        this.getAppId(),
                        orderNo,
                        walletId, walletType,
                        withdrawalMethod,
                        exchangeRate,
                        amount,
                        status
                ));
    }

    /**
     * 修改订单
     */
    public void modify() {

    }


    /**
     * 提交出金申诉
     */
    public void appeal() {

    }


    /**
     * 回撤出金
     */
    public void recall() {


        this.recallTime = Instant.now();
    }

    /**
     * 更新订单状态
     *
     * @param status 订单状态
     */
    public void updateStatus(WithdrawalOrderStatus status) {
        this.status = status;
    }


    private String generateIdemKey(String operate) {
        return operate + ":" + orderNo.rawId();
    }
}
