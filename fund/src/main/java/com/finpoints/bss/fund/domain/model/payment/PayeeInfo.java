package com.finpoints.bss.fund.domain.model.payment;

import com.finpoints.bss.common.domain.model.ValueObject;
import com.finpoints.bss.fund.domain.model.common.Email;
import com.finpoints.bss.fund.domain.model.common.Fullname;
import com.finpoints.bss.fund.domain.model.common.Phone;
import com.finpoints.bss.fund.domain.model.common.ResidentID;
import lombok.Getter;

@Getter
public class PayeeInfo extends ValueObject {

    private final Fullname name;            // 姓名

    private final Email email;              // 邮箱
    private final Phone phone;              // 电话
    private final String country;           // 国家
    private final ResidentID residentId;    // 证件号

    private final String address;           // 地址

    private final String account;           // 收款账号
    private final String bankName;          // 银行名称
    private final String swiftCode;         // 银行代码

    private final String walletType;        // 电子钱包类型
    private final String walletAccount;     // 电子钱包账号
    private final String walletAddress;     // 电子钱包地址

    public PayeeInfo(Fullname name, Email email, Phone phone, String country, ResidentID residentId,
                     String account, String address, String bankName, String swiftCode,
                     String walletType, String walletAccount, String walletAddress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.residentId = residentId;
        this.account = account;
        this.address = address;
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.walletType = walletType;
        this.walletAccount = walletAccount;
        this.walletAddress = walletAddress;
    }
}
