package com.finpoints.bss.fund.port.adapter.restapi.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplyWithdrawalRequest {

    @Schema(description = "钱包ID")
    @NotBlank(message = "钱包ID不能为空")
    private String walletId;

    @Schema(description = "钱包类型")
    @NotNull(message = "钱包类型不能为空")
    private WalletType walletType;

    @Schema(description = "出金方式")
    @JsonAlias("type")
    @NotNull(message = "出金方式不能为空")
    private WithdrawalMethod withdrawalMethod;

    @Schema(description = "出金金额")
    @NotNull(message = "出金金额不能为空")
    @Min(value = 0, message = "出金金额不能小于0")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "银行ID")
    private String bankId;

    @Schema(description = "银行卡号")
    private String bankCardId;

    @Schema(description = "订单号")
    @JsonAlias("serial")
    private String orderNo;

    @Schema(description = "MT账号")
    @JsonAlias("login")
    private String mtAccount;

    @Schema(description = "MT服务器ID")
    private String serverId;
}
