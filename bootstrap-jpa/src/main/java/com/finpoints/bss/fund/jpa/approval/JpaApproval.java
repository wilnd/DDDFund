package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalStatus;
import com.finpoints.bss.fund.domain.model.approval.ApprovalType;
import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "approval")
@AllArgsConstructor
public class JpaApproval extends JpaEntityBase {

    /**
     * 审核单ID
     */
    @Comment("钱包ID")
    @Column(length = 64, unique = true)
    private String approvalId;

    /**
     * 业务类型
     */
    @Enumerated(EnumType.STRING)
    private ApprovalType type;

    /**
     * 审核角色
     */
    @Enumerated(EnumType.STRING)
    private ApprovalRole role;

    /**
     * 关联业务订单号
     */
    @Column(length = 64)
    private String orderNo;

    /**
     * 审核状态
     */
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    /**
     * 审核人
     */
    @Embedded
    private Operator operator;

    protected JpaApproval() {
    }
}
