package com.finpoints.bss.fund.port.adapter.restapi.dto;

import com.finpoints.bss.fund.domain.model.approval.Approval;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalStatus;
import com.finpoints.bss.fund.domain.model.approval.ApprovalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalDTO {

    @Schema(description = "审核单ID")
    private final String approvalId;

    @Schema(description = "业务类型")
    private final ApprovalType type;

    @Schema(description = "审核角色")
    private final ApprovalRole role;

    @Schema(description = "关联业务订单号")
    private final String orderNo;

    @Schema(description = "审核状态")
    private ApprovalStatus status;

    @Schema(description = "审核用户ID")
    private String operatorUserId;

    @Schema(description = "审核用户名称")
    private String operatorUserName;

    @Schema(description = "审核用户角色")
    private String operatorUserRole;

    public static ApprovalDTO from(Approval approval) {
        return new ApprovalDTO(
                approval.getApprovalId().rawId(),
                approval.getType(),
                approval.getRole(),
                approval.getOrderNo(),
                approval.getStatus(),
                approval.getOperator().getUserId(),
                approval.getOperator().getUserName(),
                approval.getOperator().getRole()
        );
    }
}
