package com.finpoints.bss.fund.port.adapter.restapi.admin.dto;

import com.finpoints.bss.fund.domain.model.approval.ApprovalOrder;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalStatus;
import com.finpoints.bss.fund.domain.model.approval.ApprovalBusinessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalDTO {

    @Schema(description = "审核单ID")
    private final String approvalId;

    @Schema(description = "业务类型")
    private final ApprovalBusinessType type;

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

    public static ApprovalDTO from(ApprovalOrder approvalOrder) {
        return new ApprovalDTO(
                approvalOrder.getOrderId().rawId(),
                approvalOrder.getBusinessType(),
                approvalOrder.getRole(),
                approvalOrder.getBusinessOrderNo(),
                approvalOrder.getStatus(),
                approvalOrder.getOperator().getUserId(),
                approvalOrder.getOperator().getUserName(),
                approvalOrder.getOperator().getRole()
        );
    }
}
