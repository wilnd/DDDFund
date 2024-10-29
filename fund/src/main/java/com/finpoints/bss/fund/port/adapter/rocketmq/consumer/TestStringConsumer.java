package com.finpoints.bss.fund.port.adapter.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "${rocketmq.consumer.group}")
public class TestStringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
