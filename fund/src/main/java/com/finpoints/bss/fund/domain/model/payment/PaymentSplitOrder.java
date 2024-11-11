package com.finpoints.bss.fund.domain.model.payment;

import com.finpoints.bss.common.domain.model.Entity;
import com.finpoints.bss.fund.domain.model.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PaymentSplitOrder extends Entity {

    /**
     * 拆单订单号
     */
    private final PaymentSplitOrderId orderId;

    /**
     * 支付渠道
     */
    private final String channelId;

    /**
     * 支付渠道订单号
     */
    private final String channelOrderId;

    /**
     * 支付币种
     */
    private final Currency currency;

    /**
     * 支付金额
     */
    private final BigDecimal amount;

    /**
     * 支付渠道订单号
     */
    private String channelOrderNo;

    /**
     * 支付平台订单号
     */
    private String intepayOrderNo;

    /**
     * 实付币种
     */
    private Currency paidCurrency;

    /**
     * 支实付金额
     */
    private BigDecimal paidAmount;

    /**
     * 拆单状态
     */
    private PaymentOrderStatus status;

}
