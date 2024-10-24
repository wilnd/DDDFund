package com.finpoints.bss.fund.application.approval;

import com.finpoints.bss.fund.domain.model.approval.Approval;
import com.finpoints.bss.fund.domain.model.approval.ApprovalId;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRepository;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {

    private static final Logger log = LoggerFactory.getLogger(ApprovalService.class);
    private final CurrentRequesterService requesterService;
    private final ApprovalRepository approvalRepository;

    public ApprovalService(CurrentRequesterService requesterService,
                           ApprovalRepository approvalRepository) {
        this.requesterService = requesterService;
        this.approvalRepository = approvalRepository;
    }

    @Transactional
    public Approval approval(String anApprovalId) {
        ApprovalId approvalId = new ApprovalId(anApprovalId);
        Approval approval = approvalRepository.findById(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("Approval not found");
        }

        approval.approve(requesterService);
        log.info("Approval approved: {}, operator: {}", approvalId, approval.getOperator());

        return approvalRepository.save(approval);
    }


    @Transactional
    public Approval reject(String anApprovalId) {
        ApprovalId approvalId = new ApprovalId(anApprovalId);
        Approval approval = approvalRepository.findById(approvalId);
        if (approval == null) {
            throw new IllegalArgumentException("Approval not found");
        }

        approval.reject(requesterService);
        log.info("Approval rejected: {}, operator: {}", approvalId, approval.getOperator());

        return approvalRepository.save(approval);
    }
}
