package com.finpoints.bss.fund.domain.model.payment;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import lombok.Getter;

@Getter
public class PaymentOrder extends AggregateRoot {

    private final PaymentOrderId orderId;

    private final PaymentOrderStatus status;

    public PaymentOrder(PaymentOrderId orderId, PaymentOrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}
