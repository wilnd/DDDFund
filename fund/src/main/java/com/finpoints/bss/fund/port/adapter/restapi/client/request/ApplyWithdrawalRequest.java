package com.finpoints.bss.fund.port.adapter.restapi.client.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.finpoints.bss.fund.domain.model.wallet.WalletType;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ApplyWithdrawalRequest {

    @Schema(description = "钱包ID", example = "mock-wallet-1")
    @NotBlank(message = "钱包ID不能为空")
    private String walletId;

    @Schema(description = "钱包类型", example = "Trading")
    @NotNull(message = "钱包类型不能为空")
    private WalletType walletType;

    @Schema(description = "出金方式", example = "Bank")
    @JsonAlias("type")
    @NotNull(message = "出金方式不能为空")
    private WithdrawalMethod withdrawalMethod;

    @Schema(description = "请求时间", example = "2021-09-01T00:00:00Z")
    @NotNull(message = "请求时间不能为空")
    private Instant requestTime;

    @Schema(description = "出金金额", example = "100")
    @NotNull(message = "出金金额不能为空")
    @Min(value = 0, message = "出金金额不能小于0")
    private BigDecimal amount;

    @Schema(description = "备注", example = "test wd")
    private String remark;

    @Schema(description = "银行ID", example = "test-bank-1")
    private String bankId;

    @Schema(description = "银行卡号", example = "test-bank-card-1")
    private String bankCardId;

    @Schema(description = "订单号", example = "WD20210901000001")
    @JsonAlias("serial")
    private String orderNo;

    @Schema(description = "MT账号", example = "10001")
    @JsonAlias("login")
    private String mtAccount;

    @Schema(description = "MT服务器ID", example = "test-mt-server-1")
    private String serverId;
}
