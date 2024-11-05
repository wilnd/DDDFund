package com.finpoints.bss.fund.domain.model.mt;

import java.io.Serializable;

/**
 * 请求命令
 */
public interface MtRequestCommand extends Serializable {

    MtRequestType requestType();
}
