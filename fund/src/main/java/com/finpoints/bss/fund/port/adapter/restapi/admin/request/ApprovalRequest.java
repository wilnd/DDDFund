package com.finpoints.bss.fund.port.adapter.restapi.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApprovalRequest {

    @Schema(description = "备注")
    private String remark;
}
