package com.finpoints.bss.fund.port.adapter.restapi.admin;

import com.finpoints.bss.fund.application.approval.ApprovalService;
import com.finpoints.bss.fund.domain.model.approval.ApprovalOrder;
import com.finpoints.bss.fund.port.adapter.restapi.admin.dto.ApprovalDTO;
import com.finpoints.bss.fund.port.adapter.restapi.admin.request.ApprovalRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fund/admin/approval")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PutMapping("/{approvalId}/approve")
    @Operation(summary = "审批通过")
    public ApprovalDTO approve(@PathVariable String approvalId, @RequestBody ApprovalRequest request) {
        ApprovalOrder approvalOrder = approvalService.approve(approvalId, request.getRemark());
        return ApprovalDTO.from(approvalOrder);
    }


    @PutMapping("/{approvalId}/reject")
    @Operation(summary = "审批拒绝")
    public ApprovalDTO reject(@PathVariable String approvalId, @RequestBody ApprovalRequest request) {
        ApprovalOrder approvalOrder = approvalService.reject(approvalId, request.getRemark());
        return ApprovalDTO.from(approvalOrder);
    }
}
