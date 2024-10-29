package com.finpoints.bss.common.event;

/**
 * 外部事件，可用于发布到消息队列
 */
public interface ExternalEvent {

    /**
     * 事件主题
     */
    String topic();

    /**
     * 事件标签
     */
    default String tag() {
        return "*";
    }

    /**
     * 事件键
     */
    String key();

    /**
     * 事件内容转换器
     */
    default ExternalEventConverter converter() {
        return new ComponentPathConverter();
    }
}
