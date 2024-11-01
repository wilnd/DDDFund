package com.finpoints.bss.fund.application.approval;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import com.finpoints.bss.fund.domain.model.approval.*;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderSubmitted;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettings;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApprovalEventProcessor {

    private final ApprovalOrderRepository approvalRepository;
    private final CurrentRequesterService requesterService;
    private final WithdrawalSettingsService withdrawalSettingsService;

    public ApprovalEventProcessor(ApprovalOrderRepository approvalRepository,
                                  CurrentRequesterService requesterService,
                                  WithdrawalSettingsService withdrawalSettingsService) {
        this.approvalRepository = approvalRepository;
        this.requesterService = requesterService;
        this.withdrawalSettingsService = withdrawalSettingsService;
    }

    @ApplicationModuleListener
    public void processWithdrawalSubmitted(WithdrawalOrderSubmitted event) {
        // 幂等性检查
        ApprovalOrder approvalOrder = approvalRepository.orderApproval(ApprovalType.Withdrawal, ApprovalRole.Risk,
                event.getWithdrawalOrderNo().rawId());
        if (approvalOrder != null) {
            log.warn("Risk Approval already exists for withdrawal order {}", event.getWithdrawalOrderNo());
            return;
        }

        // 创建风控审核单
        approvalOrder = new ApprovalOrder(
                new ApprovalOrderId(IdentityGenerator.nextIdentity()),
                ApprovalType.Withdrawal, ApprovalRole.Risk,
                event.getWithdrawalOrderNo().rawId());
        log.info("Risk Approval created for withdrawal order {}, approval {}",
                event.getWithdrawalOrderNo(), approvalOrder.getOrderId());

        // 自动审核
        WithdrawalSettings settings = withdrawalSettingsService.getUserSetting(event.getUserId());
        if (BooleanUtils.isTrue(settings.getAutoApproval())) {
            approvalOrder.approve(requesterService);
            log.info("Auto approved withdrawal order {}, approval {}",
                    event.getWithdrawalOrderNo(), approvalOrder.getOrderId());
        }
        approvalRepository.save(approvalOrder);
    }

    @ApplicationModuleListener(condition = "#event.type.name() == 'Withdrawal' and #event.role.name() == 'Risk'")
    public void processWithdrawalRiskApproved(ApprovalOrderApproved event) {
        // 幂等性检查
        ApprovalOrder approvalOrder = approvalRepository.orderApproval(ApprovalType.Withdrawal, ApprovalRole.Finance,
                event.getOrderNo());
        if (approvalOrder != null) {
            log.warn("Finance Approval already exists for withdrawal order {}", event.getOrderNo());
            return;
        }

        // 创建财务审核单
        approvalOrder = new ApprovalOrder(
                new ApprovalOrderId(IdentityGenerator.nextIdentity()),
                ApprovalType.Withdrawal, ApprovalRole.Finance,
                event.getOrderNo());
        log.info("Finance Approval created for withdrawal order {}, approval {}",
                event.getOrderNo(), approvalOrder.getOrderId());

        approvalRepository.save(approvalOrder);
    }
}
