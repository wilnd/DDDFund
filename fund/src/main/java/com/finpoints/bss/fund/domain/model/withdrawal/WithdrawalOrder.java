package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.DomainEventPublisher;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.wallet.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

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
     * 订单状态
     */
    private WithdrawalOrderStatus status;

    /**
     * MT出金请求ID，仅在出金订单为MT出金时有值
     */
    private final MTWithdrawalRequestId mtRequestId;

    /**
     * 冻结流水ID
     */
    private FrozenTransactionId frozenTransactionId;

    /**
     * 创建出金订单
     */
    public WithdrawalOrder(WithdrawalOrderNo orderNo, UserId userId, WalletId walletId, WalletType walletType,
                           WithdrawalMethod withdrawalMethod, BigDecimal exchangeRate, Currency originalCurrency,
                           Currency targetCurrency, BigDecimal amount, MTWithdrawalRequestId mtRequestId) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.walletId = walletId;
        this.walletType = walletType;
        this.withdrawalMethod = withdrawalMethod;
        this.exchangeRate = exchangeRate;
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.mtRequestId = mtRequestId;
        this.status = WithdrawalOrderStatus.CREATED;
    }

    /**
     * 提交出金订单
     * 提交出金订单后，订单状态变为待审核
     */
    public void submit(WalletOperationService walletOperationService) {
        Validate.isTrue(this.status == WithdrawalOrderStatus.CREATED, "Expected status is CREATED");

        // 冻结出金金额
        this.frozenTransactionId = walletOperationService.freezeWalletAmount(
                this.getWalletId(),
                FrozenType.Withdrawal, amount,
                generateIdemKey("submitWithdrawal"),
                "");
        this.updateStatus(WithdrawalOrderStatus.PENDING);

        // 发布出金订单提交事件
        DomainEventPublisher.instance()
                .publish(new WithdrawalOrderSubmitted(
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
        Validate.isTrue(this.status == WithdrawalOrderStatus.PENDING || this.status == WithdrawalOrderStatus.APPROVING,
                "Expected status is PENDING or APPROVING");

        // 解冻出金金额
        walletOperationService.unfreezeWalletAmount(
                walletId, frozenTransactionId,
                FrozenType.WithdrawalCancel, amount,
                generateIdemKey("cancelWithdrawal"),
                "");
        this.updateStatus(status);

        // 发布出金订单取消事件
        DomainEventPublisher.instance()
                .publish(new WithdrawalOrderCancelled(
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
