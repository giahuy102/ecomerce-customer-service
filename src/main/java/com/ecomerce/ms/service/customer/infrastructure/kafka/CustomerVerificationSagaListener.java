package com.ecomerce.ms.service.customer.infrastructure.kafka;

import com.ecomerce.ms.service.CustomerVerificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.ecomerce.ms.service.customer.domain.shared.Constants.CUSTOMER_VERIFICATION_TOPIC;

@Component
public class CustomerVerificationSagaListener {

    @KafkaListener(topics = CUSTOMER_VERIFICATION_TOPIC)
    public void onCustomerVerificationEvent(@Payload CustomerVerificationEvent customerVerificationEvent) {

    }
}
