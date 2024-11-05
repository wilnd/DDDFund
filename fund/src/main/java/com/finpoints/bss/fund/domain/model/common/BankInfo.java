package com.finpoints.bss.fund.domain.model.common;

import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class BankInfo extends ValueObject {

    private final String bankName;  // 银行名称
    private final String bankCode;  // 银行代码
    private final String address;   // 银行地址
    private final String swiftCode; // 银行SWIFT代码，用于国际汇款
    private final String remark;    // 备注

    public BankInfo(String bankName, String bankCode, String address,
                    String swiftCode, String remark) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.address = address;
        this.swiftCode = swiftCode;
        this.remark = remark;
    }
}
