package com.finpoints.bss.fund.domain.model.approval;

import com.finpoints.bss.common.domain.model.Operator;
import lombok.Getter;

/**
 * 审核角色
 */
@Getter
public enum ApprovalRole {

    RISK("", "风控"),
    FINANCE("", "财务"),
    ;

    private final String role;
    private final String desc;

    ApprovalRole(String role, String desc) {
        this.role = role;
        this.desc = desc;
    }

    public boolean canOperate(String role) {
        if (Operator.SYSTEM_ROLE.equals(role)) {
            return true;
        }
        return this.role.equals(role);
    }
}
