package com.finpoints.bss.fund.domain.model.event;

import com.finpoints.bss.common.domain.model.AbstractId;

public class EventRecordId extends AbstractId {

    public EventRecordId(String id) {
        super(id);
    }

    public static EventRecordId of(String id) {
        return new EventRecordId(id);
    }
}
