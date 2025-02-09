package com.finpoints.bss.fund.port.adapter.restapi.client;

import com.finpoints.bss.common.requester.CurrentRequesterService;
import com.finpoints.bss.fund.application.withdrawal.ClientWithdrawalService;
import com.finpoints.bss.fund.application.withdrawal.WithdrawalService;
import com.finpoints.bss.fund.application.withdrawal.command.ApplyWithdrawalCommand;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrder;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderNo;
import com.finpoints.bss.fund.port.adapter.restapi.client.dto.WithdrawalOrderDTO;
import com.finpoints.bss.fund.port.adapter.restapi.client.request.ApplyWithdrawalRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fund/client/withdrawal")
public class WithdrawalController {

    private final CurrentRequesterService requesterService;
    private final ClientWithdrawalService withdrawalService;

    public WithdrawalController(CurrentRequesterService requesterService,
                                WithdrawalService withdrawalService) {
        this.requesterService = requesterService;
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("/orders")
    @Operation(summary = "申请出金", description = "申请出金")
    public String applyWithdrawal(@RequestBody ApplyWithdrawalRequest request) {
        WithdrawalOrderNo orderNo = withdrawalService.applyWithdrawal(
                ApplyWithdrawalCommand.builder()
                        .appId(requesterService.currentAppId())
                        .orderNo(request.getOrderNo())
                        .userId(requesterService.currentUserId())
                        .walletId(request.getWalletId())
                        .walletType(request.getWalletType())
                        .withdrawalMethod(request.getWithdrawalMethod())
                        .requestTime(request.getRequestTime())
                        .amount(request.getAmount())
                        .remark(request.getRemark())
                        .bankId(request.getBankId())
                        .bankCardId(request.getBankCardId())
                        .serverId(request.getServerId())
                        .mtAccount(request.getMtAccount())
                        .build());
        return orderNo == null ? null : orderNo.rawId();
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "取消出金", description = "取消出金")
    public WithdrawalOrderDTO cancelWithdrawal(@PathVariable String orderId) {
        WithdrawalOrder order = withdrawalService.cancelWithdrawal(requesterService.currentUserId(), orderId);
        return WithdrawalOrderDTO.from(order);
    }
}
