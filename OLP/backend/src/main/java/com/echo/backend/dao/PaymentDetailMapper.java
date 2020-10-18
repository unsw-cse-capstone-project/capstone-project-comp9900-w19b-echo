package com.echo.backend.dao;

import com.echo.backend.domain.PaymentDetail;

import java.util.List;

public interface PaymentDetailMapper {

    void addPaymentDetail(PaymentDetail paymentDetail);

    List<PaymentDetail> getPaymentDetailByUid(int uid);

}
