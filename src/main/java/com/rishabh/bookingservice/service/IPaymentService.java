package com.rishabh.bookingservice.service;

public interface IPaymentService {
    boolean doPayment();

    boolean retryPayment(int retryCount);
}
