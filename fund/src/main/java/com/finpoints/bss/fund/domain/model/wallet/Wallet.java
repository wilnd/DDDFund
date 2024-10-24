package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Wallet extends AggregateRoot {

    /**
     * 钱包ID
     */
    private final WalletId walletId;

    /**
     * 用户ID
     */
    private final UserId userId;

    /**
     * 币种
     */
    private final Currency currency;

    /**
     * 钱包类型
     */
    private final WalletType type;

    /**
     * 是否为主钱包，默认否
     */
    private final Boolean mainWallet;

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


    public Wallet(WalletId walletId, UserId userId, Currency currency, WalletType type, Boolean mainWallet) {
        this.walletId = walletId;
        this.userId = userId;
        this.currency = currency;
        this.type = type;
        this.mainWallet = mainWallet;

        this.balance = BigDecimal.ZERO;
        this.frozenBalance = BigDecimal.ZERO;
        this.availableBalance = BigDecimal.ZERO;
        this.drawableBalance = BigDecimal.ZERO;
    }

    /**
     * 冻结资金
     * <p>
     * 增加冻结金额
     * 扣除可用/可提金额
     *
     * @param type    冻结类型
     * @param amount  冻结金额
     * @param idemKey 幂等key
     * @param remark  备注
     * @return 冻结流水
     */
    public FrozenTransaction freeze(FrozenType type, BigDecimal amount, String idemKey, String remark) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.availableBalance = availableBalance.subtract(amount);
        this.drawableBalance = drawableBalance.subtract(amount);
        this.frozenBalance = frozenBalance.add(amount);

        return new FrozenTransaction(
                new FrozenTransactionId(IdentityGenerator.nextIdentity()),
                idemKey,
                this.getWalletId(),
                this.getUserId(),
                this.getCurrency(),
                type,
                amount,
                remark
        );
    }

    /**
     * 解冻资金
     * <p>
     * 减少冻结金额
     * 增加可用/可提金额
     * 如果存在服务费，余额/可用/可提扣除服务费
     *
     * @param type        解冻类型
     * @param transaction 冻结流水
     * @param remark      备注
     * @return 冻结流水
     */
    public FrozenTransaction unfreeze(FrozenType type, FrozenTransaction transaction, String remark) {
        if (transaction == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (transaction.getAmount().compareTo(frozenBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.frozenBalance = frozenBalance.subtract(transaction.getAmount());
        this.drawableBalance = drawableBalance.add(transaction.getAmount());
        this.availableBalance = availableBalance.add(transaction.getAmount());

        transaction.unfreeze(type, remark);
        return transaction;
    }

    /**
     * 增加冻结资金
     * <p>
     * 增加冻结金额
     * 增加余额
     *
     * @param amount 增加金额
     * @return 增加后的冻结金额
     */
    public FrozenTransaction increaseFrozen(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        this.frozenBalance = frozenBalance.add(amount);
        this.balance = balance.add(amount);

        // TODO: 冻结流水
        return null;
    }

    /**
     * 扣除冻结资金
     * <p>
     * 扣除冻结金额
     * 扣除余额
     *
     * @param amount 扣除金额
     * @return 扣除后的可用金额
     */
    public FrozenTransaction deductFrozen(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount.compareTo(frozenBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.frozenBalance = frozenBalance.subtract(amount);
        this.balance = balance.subtract(amount);

        // TODO: 冻结流水
        return null;
    }

    /**
     * 增加可用资金
     * <p>
     * 增加可用金额
     * 增加余额
     *
     * @param amount 增加金额
     * @return 增加后的可用金额
     */
    public WalletTransaction increase(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        this.availableBalance = availableBalance.add(amount);
        this.drawableBalance = drawableBalance.add(amount);
        this.balance = balance.add(amount);

        // TODO: 交易流水
        return null;
    }


    /**
     * 减少可用资金
     * <p>
     * 减少可用金额
     * 减少余额
     *
     * @param amount 提现金额
     * @return 提现后的可用金额
     */
    public WalletTransaction deduct(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.availableBalance = availableBalance.subtract(amount);
        this.drawableBalance = drawableBalance.subtract(amount);
        this.balance = balance.subtract(amount);

        // TODO: 交易流水
        return null;
    }
}
