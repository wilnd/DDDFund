package com.finpoints.bss.fund.domain.model.mt;

import com.fasterxml.jackson.databind.JsonNode;
import com.finpoints.bss.common.domain.model.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MtRequest extends AggregateRoot {

    /**
     * MT请求ID
     */
    private final MtRequestId requestId;

    /**
     * 请求服务器类型
     */
    private final MtServerType serverType;

    /**
     * 请求服务器ID
     */
    private final MtServerId serverId;

    /**
     * MT交易账户
     */
    private final String account;

    /**
     * 请求类型
     */
    private final MtRequestType type;

    /**
     * 请求命令
     */
    private final MtRequestCommand command;

    /**
     * 请求状态
     */
    private MtRequestStatus status;

    /**
     * 请求返回消息
     */
    private String responseMessage;

    /**
     * 请求返回内容
     */
    private JsonNode responseContent;

    public MtRequest(MtRequestId requestId, MtServerId serverId, String account, MtRequestCommand command) {
        this.requestId = requestId;
        this.account = account;
        this.serverType = null;
        this.serverId = serverId;
        this.type = command.requestType();
        this.command = command;
        this.status = MtRequestStatus.Pending;

        this.registerEvent(new MtRequestCreated(
                this.requestId,
                this.serverType,
                this.serverId,
                this.account,
                this.type,
                this.command,
                this.status
        ));
    }

    public void emit(MtRequesterService requesterService) {
        MtRequestRes res = requesterService.request(this.requestId, this.serverType, this.serverId, this.command);
        if (res != null) {
            this.status = res.getStatus();
            this.responseMessage = res.getMessage();
            this.responseContent = res.getContent();
        }
    }
}
