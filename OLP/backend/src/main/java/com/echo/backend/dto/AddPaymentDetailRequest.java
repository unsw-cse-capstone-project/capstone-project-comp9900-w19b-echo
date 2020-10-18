package com.echo.backend.dto;

import com.echo.backend.domain.PaymentDetail;

public class AddPaymentDetailRequest {

    private PaymentDetail paymentDetail;

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
}
