package com.finpoints.bss.fund.domain.model.wallet;

import com.finpoints.bss.common.domain.model.AbstractId;
import lombok.Getter;

@Getter
public class WalletId extends AbstractId {

    public WalletId(String anId) {
        super(anId);
    }
}
