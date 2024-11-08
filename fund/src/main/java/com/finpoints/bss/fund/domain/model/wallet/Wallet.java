package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.domain.model.common.Currency;
import com.finpoints.bss.fund.domain.model.common.UserId;
import com.finpoints.bss.fund.domain.model.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Wallet extends AggregateRoot {

    /**
     * 钱包ID
     */
    private final WalletId walletId;

    /**
     * 用户角色
     */
    private final UserRole userRole;

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


    public Wallet(String appId, WalletId walletId, UserRole userRole, UserId userId, Currency currency,
                  WalletType type, Boolean mainWallet) {
        super(appId);

        this.walletId = walletId;
        this.userRole = userRole;
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
     * @param orderNo 业务订单号
     * @param remark  备注
     * @return 冻结流水
     */
    public FrozenFlow freeze(FrozenFlowId flowId, FrozenType type, BigDecimal amount, String orderNo, String remark) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        this.availableBalance = availableBalance.subtract(amount);
        this.drawableBalance = drawableBalance.subtract(amount);
        this.frozenBalance = frozenBalance.add(amount);

        return new FrozenFlow(
                this.getAppId(),
                flowId,
                this.getWalletId(),
                this.getUserId(),
                orderNo,
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
     * @param type   解冻类型
     * @param flow   冻结流水
     * @param remark 备注
     * @return 冻结流水
     */
    public FrozenFlow unfreeze(FrozenType type, FrozenFlow flow, String remark) {
        if (flow == null || flow.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (flow.getAmount().compareTo(frozenBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        if (flow.getStatus() != FrozenStatus.FROZEN) {
            throw new IllegalArgumentException("The flow is not frozen");
        }

        this.frozenBalance = frozenBalance.subtract(flow.getAmount());
        this.drawableBalance = drawableBalance.add(flow.getAmount());
        this.availableBalance = availableBalance.add(flow.getAmount());

        // 解冻流水
        flow.unfreeze(type, remark);
        return flow;
    }

    /**
     * 增加冻结资金
     * <p>
     * 增加冻结金额
     * 增加余额
     *
     * @param flowId     流水ID
     * @param frozenFlow 冻结流水
     * @param remark     备注
     * @return 增加后的冻结金额
     */
    public WalletFlow increaseFrozen(WalletFlowId flowId, FrozenFlow frozenFlow, String remark) {
        Validate.notNull(flowId, "FlowId must not be null");
        Validate.notNull(frozenFlow, "FrozenFlow must not be null");

        if (frozenFlow.getAmount() == null || frozenFlow.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (frozenFlow.getStatus() != FrozenStatus.FROZEN) {
            throw new IllegalArgumentException("The flow is not frozen");
        }

        BigDecimal originalBalance = this.getBalance();
        this.frozenBalance = frozenBalance.add(frozenFlow.getAmount());
        this.balance = balance.add(frozenFlow.getAmount());

        return new WalletFlow(
                this.getAppId(),
                flowId,
                this.getType(),
                this.getWalletId(),
                this.getUserRole(),
                this.getUserId(),
                WalletFlowType.from(frozenFlow.getFreezeType()),
                frozenFlow.getBusinessOrderNo(),
                this.getCurrency(),
                originalBalance,
                frozenFlow.getAmount(),
                this.getBalance(),
                remark,
                Operator.current()
        );
    }

    /**
     * 扣除冻结资金
     * <p>
     * 扣除冻结金额
     * 扣除余额
     *
     * @param flowId     流水ID
     * @param frozenFlow 冻结流水
     * @return 扣除后的可用金额
     */
    public WalletFlow deductFrozen(WalletFlowId flowId, FrozenFlow frozenFlow, String remark) {
        Validate.notNull(flowId, "FlowId must not be null");
        Validate.notNull(frozenFlow, "FrozenFlow must not be null");

        if (frozenFlow.getAmount() == null || frozenFlow.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (frozenFlow.getAmount().compareTo(frozenBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        if (frozenFlow.getStatus() != FrozenStatus.FROZEN) {
            throw new IllegalArgumentException("The flow is not frozen");
        }

        BigDecimal originalBalance = this.getBalance();
        this.frozenBalance = frozenBalance.subtract(frozenFlow.getAmount());
        this.balance = balance.subtract(frozenFlow.getAmount());

        return new WalletFlow(
                this.getAppId(),
                flowId,
                this.getType(),
                this.getWalletId(),
                this.getUserRole(),
                this.getUserId(),
                WalletFlowType.from(frozenFlow.getFreezeType()),
                frozenFlow.getBusinessOrderNo(),
                this.getCurrency(),
                originalBalance,
                frozenFlow.getAmount(),
                this.getBalance(),
                remark,
                Operator.current()
        );
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
    public WalletFlow increase(WalletFlowId flowId, WalletFlowType flowType, BigDecimal amount,
                               String orderNo, String remark) {
        Validate.notNull(flowId, "FlowId must not be null");
        Validate.notNull(flowType, "FlowType must not be null");
        Validate.notNull(amount, "Amount must not be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        BigDecimal originalBalance = this.getBalance();
        this.availableBalance = availableBalance.add(amount);
        this.drawableBalance = drawableBalance.add(amount);
        this.balance = balance.add(amount);

        return new WalletFlow(
                this.getAppId(),
                flowId,
                this.getType(),
                this.getWalletId(),
                this.getUserRole(),
                this.getUserId(),
                flowType,
                orderNo,
                this.getCurrency(),
                originalBalance,
                amount,
                this.getBalance(),
                remark,
                Operator.current()
        );
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
    public WalletFlow deduct(WalletFlowId flowId, WalletFlowType flowType, BigDecimal amount,
                             String orderNo, String remark) {
        Validate.notNull(flowId, "FlowId must not be null");
        Validate.notNull(flowType, "FlowType must not be null");
        Validate.notNull(amount, "Amount must not be null");

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        BigDecimal originalBalance = this.getBalance();
        this.availableBalance = availableBalance.subtract(amount);
        this.drawableBalance = drawableBalance.subtract(amount);
        this.balance = balance.subtract(amount);

        return new WalletFlow(
                this.getAppId(),
                flowId,
                this.getType(),
                this.getWalletId(),
                this.getUserRole(),
                this.getUserId(),
                flowType,
                orderNo,
                this.getCurrency(),
                originalBalance,
                amount,
                this.getBalance(),
                remark,
                Operator.current()
        );
    }
}
