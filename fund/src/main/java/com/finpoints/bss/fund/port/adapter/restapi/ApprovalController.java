package com.finpoints.bss.fund.port.adapter.restapi;

import com.finpoints.bss.fund.application.approval.ApprovalService;
import com.finpoints.bss.fund.domain.model.approval.Approval;
import com.finpoints.bss.fund.port.adapter.restapi.dto.ApprovalDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fund/approval")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PutMapping("/{approvalId}/approve")
    @Operation(summary = "审批通过")
    public ApprovalDTO approve(@PathVariable String approvalId) {
        Approval approval = approvalService.approval(approvalId);
        return ApprovalDTO.from(approval);
    }


    @PutMapping("/{approvalId}/reject")
    @Operation(summary = "审批拒绝")
    public ApprovalDTO reject(@PathVariable String approvalId) {
        Approval approval = approvalService.reject(approvalId);
        return ApprovalDTO.from(approval);
    }
}
