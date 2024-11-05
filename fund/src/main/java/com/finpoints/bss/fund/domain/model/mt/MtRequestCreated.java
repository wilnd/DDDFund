package com.finpoints.bss.fund.domain.model.mt;

import com.finpoints.bss.common.domain.model.DomainEvent;
import com.finpoints.bss.common.domain.model.DomainEventModule;
import lombok.Getter;

@Getter
public class MtRequestCreated extends DomainEvent {

    private final MtRequestId requestId;
    private final MtServerType serverType;
    private final MtServerId serverId;
    private final String account;
    private final MtRequestType type;
    private final MtRequestCommand command;
    private final MtRequestStatus status;

    public MtRequestCreated(MtRequestId requestId, MtServerType serverType, MtServerId serverId,
                            String account, MtRequestType type, MtRequestCommand command, MtRequestStatus status) {
        this.requestId = requestId;
        this.serverType = serverType;
        this.serverId = serverId;
        this.account = account;
        this.type = type;
        this.command = command;
        this.status = status;
    }

    @Override
    public DomainEventModule module() {
        return DomainEventModule.MT;
    }
}
