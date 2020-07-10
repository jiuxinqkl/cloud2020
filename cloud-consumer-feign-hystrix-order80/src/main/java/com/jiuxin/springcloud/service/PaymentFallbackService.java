package com.jiuxin.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_ok(Integer id) {
        return "---------PaymentFallbackService----fallback--paymentInfo_ok";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "---------PaymentFallbackService----fallback--paymentInfo_Timeout";
    }
}
