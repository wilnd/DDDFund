package com.finpoints.bss.fund.application.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalOrder;
import com.finpoints.bss.fund.domain.model.approval.ApprovalOrderId;
import com.finpoints.bss.fund.domain.model.approval.ApprovalOrderRepository;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {

    private static final Logger log = LoggerFactory.getLogger(ApprovalService.class);
    private final CurrentRequesterService requesterService;
    private final ApprovalOrderRepository approvalRepository;

    public ApprovalService(CurrentRequesterService requesterService,
                           ApprovalOrderRepository approvalRepository) {
        this.requesterService = requesterService;
        this.approvalRepository = approvalRepository;
    }

    @Transactional
    public ApprovalOrder approval(String anApprovalId) {
        ApprovalOrderId approvalOrderId = new ApprovalOrderId(anApprovalId);
        ApprovalOrder approvalOrder = approvalRepository.findById(approvalOrderId);
        if (approvalOrder == null) {
            throw new IllegalArgumentException("Approval not found");
        }

        approvalOrder.approve(requesterService);
        log.info("Approval approved: {}, operator: {}", approvalOrderId, approvalOrder.getOperator());

        return approvalRepository.save(approvalOrder);
    }


    @Transactional
    public ApprovalOrder reject(String anApprovalId) {
        ApprovalOrderId approvalOrderId = new ApprovalOrderId(anApprovalId);
        ApprovalOrder approvalOrder = approvalRepository.findById(approvalOrderId);
        if (approvalOrder == null) {
            throw new IllegalArgumentException("Approval not found");
        }

        approvalOrder.reject(requesterService);
        log.info("Approval rejected: {}, operator: {}", approvalOrderId, approvalOrder.getOperator());

        return approvalRepository.save(approvalOrder);
    }
}
