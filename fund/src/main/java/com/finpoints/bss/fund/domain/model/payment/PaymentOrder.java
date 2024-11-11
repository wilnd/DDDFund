package com.finpoints.bss.fund.domain.model.payment;

import com.finpoints.bss.common.domain.model.AggregateRoot;
import com.finpoints.bss.fund.domain.model.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PaymentOrder extends AggregateRoot {

    /**
     * 支付订单号
     */
    private final PaymentOrderId orderId;

    /**
     * 订单类型（打款方式）
     */
    private final PaymentOrderType orderType;

    /**
     * 业务类型
     */
    private final PaymentBusinessType businessType;

    /**
     * 业务订单号
     */
    private final String businessOrderNo;

    /**
     * 商户ID
     */
    private final String merchantId;

    /**
     * 支付渠道
     */
    private final String channelId;

    /**
     * 支付渠道编码
     */
    private final String channelCode;

    /**
     * 支付方式
     */
    private final PaymentMethod paymentMethod;

    /**
     * 收款人信息
     */
    private final PayeeInfo payee;

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
     * 支付订单状态
     */
    private PaymentOrderStatus status;

    /**
     * 支付时间
     */
    private Instant paymentTime;

    /**
     * 拆单订单
     */
    private final List<PaymentSplitOrder> splitOrders;

    public PaymentOrder(String appId, PaymentOrderId orderId, PaymentOrderType orderType,
                        PaymentBusinessType businessType, String businessOrderNo,
                        String merchantId, String channelId, String channelCode,
                        PaymentMethod paymentMethod, PayeeInfo payee,
                        Currency currency, BigDecimal amount) {
        super(appId);
        this.orderId = orderId;
        this.orderType = orderType;
        this.businessType = businessType;
        this.businessOrderNo = businessOrderNo;
        this.merchantId = merchantId;
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.paymentMethod = paymentMethod;
        this.payee = payee;
        this.currency = currency;
        this.amount = amount;
        this.status = PaymentOrderStatus.PENDING;
        this.splitOrders = new ArrayList<>();
    }


}
