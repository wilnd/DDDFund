package com.finpoints.bss.fund.jpa.mt;

import com.fasterxml.jackson.databind.JsonNode;
import com.finpoints.bss.fund.domain.model.mt.MtRequestStatus;
import com.finpoints.bss.fund.domain.model.mt.MtRequestType;
import com.finpoints.bss.fund.domain.model.mt.MtServerType;
import com.finpoints.bss.fund.jpa.JpaEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@Table(name = "mt_request")
@AllArgsConstructor
public class JpaMtRequest extends JpaEntityBase {


    /**
     * MT请求ID
     */
    private String requestId;

    /**
     * 请求服务器类型
     */
    private MtServerType serverType;

    /**
     * 请求服务器ID
     */
    private String serverId;

    /**
     * MT交易账户
     */
    private String account;

    /**
     * 请求类型
     */
    private MtRequestType type;

    /**
     * 请求命令
     */
    private String command;

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
    private String responseContent;

    protected JpaMtRequest() {
    }
}
