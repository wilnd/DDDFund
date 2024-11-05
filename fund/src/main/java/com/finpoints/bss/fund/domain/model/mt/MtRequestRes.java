package com.finpoints.bss.fund.domain.model.mt;

import com.fasterxml.jackson.databind.JsonNode;
import com.finpoints.bss.common.domain.model.ValueObject;
import lombok.Getter;

@Getter
public class MtRequestRes extends ValueObject {

    private final MtRequestStatus status;
    private final String message;

    private final JsonNode content;

    public MtRequestRes(MtRequestStatus status, String message, JsonNode content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }
}
