package com.finpoints.bss.fund.domain.model.mt;

public interface MtRequesterService {

    /**
     * 发起MT请求
     *
     * @param requestId  请求ID
     * @param serverType 服务器类型
     * @param serverId   服务器ID
     * @param command    请求命令
     * @return MT请求
     */
    MtRequestRes request(MtRequestId requestId, MtServerType serverType, MtServerId serverId, MtRequestCommand command);


}
