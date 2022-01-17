package com.rishabh.bookingservice.service.impl;

import com.rishabh.bookingservice.service.IPaymentService;

public class PaymentServiceImpl implements IPaymentService {
    @Override
    public boolean doPayment() {
        return true;
    }

    @Override
    public boolean retryPayment(int retryCount) {
        boolean retryStatus = false;
        for (int i = 0; i < retryCount; i++) {
            retryStatus = doPayment();
        }
        return retryStatus;
    }
}
