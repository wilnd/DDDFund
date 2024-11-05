package com.finpoints.bss.fund.port.adapter.restapi.client.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WithdrawalOrderDTO {

    @Schema(description = "出金订单号")
    private String orderSeries;

    @Schema(description = "用户ID")
    @JsonAlias("uuid")
    private String userId;

    @Schema(description = "出金钱包类型")
    private WalletType walletType;

    @Schema(description = "出金钱包ID")
    private String walletId;

    @Schema(description = "出金方式")
    private WithdrawalMethod withdrawalMethod;

    @Schema(description = "汇率")
    private BigDecimal exchangeRate;

    @Schema(description = "出金币种")
    private String originalCurrency;

    @Schema(description = "出金币种")
    private String targetCurrency;

    @Schema(description = "出金金额")
    private BigDecimal amount;

    @Schema(description = "订单状态")
    private WithdrawalOrderStatus status;

    @Schema(description = "创建时间")
    private Instant createdTime;

    @Schema(description = "更新时间")
    private Instant updatedTime;

    public static WithdrawalOrderDTO from(WithdrawalOrder order) {
        return new WithdrawalOrderDTO(
                order.getOrderNo().rawId(),
                order.getUserId().rawId(),
                order.getWalletType(),
                order.getWalletId().rawId(),
                order.getWithdrawalMethod(),
                order.getExchangeRate(),
                order.getOriginalCurrency().getCode(),
                order.getTargetCurrency().getCode(),
                order.getAmount(),
                order.getStatus(),
                order.getCreatedTime(),
                order.getUpdatedTime()
        );
    }
}
