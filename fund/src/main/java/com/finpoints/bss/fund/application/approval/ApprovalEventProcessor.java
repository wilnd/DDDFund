package com.finpoints.bss.fund.application.approval;

import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import com.finpoints.bss.fund.domain.model.approval.*;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderCancelled;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalOrderSubmitted;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettings;
import com.finpoints.bss.fund.domain.model.withdrawal.WithdrawalSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 处理出金订单提交事件
     */
    @ApplicationModuleListener
    public void processWithdrawalSubmitted(WithdrawalOrderSubmitted event) {
        // 幂等性检查
        ApprovalOrder approvalOrder = approvalRepository.orderApproval(ApprovalBusinessType.WITHDRAWAL, ApprovalRole.RISK,
                event.getWithdrawalOrderNo().rawId());
        if (approvalOrder != null) {
            log.warn("Risk Approval already exists for withdrawal order {}", event.getWithdrawalOrderNo());
            return;
        }

        // 创建风控审核单
        approvalOrder = new ApprovalOrder(
                event.appId(),
                new ApprovalOrderId(IdentityGenerator.nextIdentity()),
                ApprovalBusinessType.WITHDRAWAL, ApprovalRole.RISK,
                event.getWithdrawalOrderNo().rawId());
        log.info("Risk Approval created for withdrawal order {}, approval {}",
                event.getWithdrawalOrderNo(), approvalOrder.getOrderId());

        // 自动审核
        WithdrawalSettings settings = withdrawalSettingsService.getUserSetting(event.getUserId());
        if (BooleanUtils.isTrue(settings.getAutoApproval())) {
            approvalOrder.approve(requesterService, "Auto approved");
            log.info("Auto approved withdrawal order {}, approval {}",
                    event.getWithdrawalOrderNo(), approvalOrder.getOrderId());
        }
        approvalRepository.save(approvalOrder);
    }

    /**
     * 处理出金订单取消事件
     */
    @ApplicationModuleListener(condition = "#event.status.name() == 'CANCELLED'")
    public void processWithdrawalCancelled(WithdrawalOrderCancelled event) {
        // 取消此订单下所有可取消的审核单
        List<ApprovalOrder> approvalOrders = approvalRepository.orderApprovals(ApprovalBusinessType.WITHDRAWAL,
                event.getWithdrawalOrderNo().rawId());
        approvalOrders.forEach(approvalOrder -> {
            if (approvalOrder.canCancel()) {
                approvalOrder.cancel();
                log.info("Approval cancelled for withdrawal order {}, approval {}",
                        event.getWithdrawalOrderNo(), approvalOrder.getOrderId());
            }
        });
        approvalRepository.saveAll(approvalOrders);
    }

    /**
     * 处理出金订单风控审核通过事件
     */
    @ApplicationModuleListener(condition = "#event.businessType.name() == 'Withdrawal' and #event.role.name() == 'Risk'")
    public void processWithdrawalRiskApproved(ApprovalOrderApproved event) {
        // 幂等性检查
        ApprovalOrder approvalOrder = approvalRepository.orderApproval(ApprovalBusinessType.WITHDRAWAL, ApprovalRole.FINANCE,
                event.getBusinessOrderNo());
        if (approvalOrder != null) {
            log.warn("Finance Approval already exists for withdrawal order {}", event.getBusinessOrderNo());
            return;
        }

        // 创建财务审核单
        approvalOrder = new ApprovalOrder(
                event.appId(),
                new ApprovalOrderId(IdentityGenerator.nextIdentity()),
                ApprovalBusinessType.WITHDRAWAL, ApprovalRole.FINANCE,
                event.getBusinessOrderNo());
        log.info("Finance Approval created for withdrawal order {}, approval {}",
                event.getBusinessOrderNo(), approvalOrder.getOrderId());

        approvalRepository.save(approvalOrder);
    }
}
