package com.finpoints.bss.fund.domain.model.withdrawal;

import com.finpoints.bss.common.domain.model.AbstractId;

public class MTWithdrawalRequestId extends AbstractId {

    public MTWithdrawalRequestId(String id) {
        super(id);
    }

    public static MTWithdrawalRequestId of(String id) {
        if (id == null) {
            return null;
        }
        return new MTWithdrawalRequestId(id);
    }
}
