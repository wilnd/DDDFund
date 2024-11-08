package com.finpoints.bss.fund.domain.model.mt.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.finpoints.bss.fund.domain.model.mt.MtRequestCommand;
import com.finpoints.bss.fund.domain.model.mt.MtRequestType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MtWithdrawalCommand implements MtRequestCommand {

    private final BigDecimal amount;
    private final String comment;

    @JsonCreator
    public MtWithdrawalCommand(BigDecimal amount, String comment) {
        this.amount = amount;
        this.comment = comment;
    }

    @Override
    public MtRequestType requestType() {
        return MtRequestType.WITHDRAWAL;
    }
}
