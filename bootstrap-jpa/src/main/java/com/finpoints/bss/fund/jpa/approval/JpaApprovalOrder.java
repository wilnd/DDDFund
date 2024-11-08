package com.finpoints.bss.fund.jpa.approval;

import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.fund.domain.model.approval.ApprovalRole;
import com.finpoints.bss.fund.domain.model.approval.ApprovalStatus;
import com.finpoints.bss.fund.domain.model.approval.ApprovalBusinessType;
import com.finpoints.bss.fund.jpa.JpaVersionalEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Entity
@Table(name = "approval")
@AllArgsConstructor
public class JpaApprovalOrder extends JpaVersionalEntityBase {

    @Comment("审核单ID")
    @Column(length = 64, unique = true)
    private String orderId;

    @Comment("审核角色")
    @Enumerated(EnumType.STRING)
    private ApprovalRole role;

    @Comment("业务类型")
    @Enumerated(EnumType.STRING)
    private ApprovalBusinessType businessType;

    @Comment("业务单号")
    @Column(length = 64)
    private String businessOrderNo;

    @Comment("审核状态")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Comment("审核时间")
    private Instant approvalTime;

    @Comment("审核备注")
    private String remark;

    @Embedded
    private Operator operator;

    protected JpaApprovalOrder() {
    }
}
