package com.finpoints.bss.fund.domain.model.common;

public interface BankInfoService {

    BankInfo bankInfo(BankId bankId);

    BankCardInfo bankCardInfo(BankCardId bankCardId);
}
