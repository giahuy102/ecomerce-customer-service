package com.ecomerce.ms.service.customer.infrastructure.kafka;

import com.ecomerce.ms.service.CustomerVerificationEvent;
import com.ecomerce.ms.service.CustomerVerificationReply;
import com.ecomerce.ms.service.Metadata;
import com.ecomerce.ms.service.OrderSagaKey;
import com.ecomerce.ms.service.SagaStepStatus;
import com.ecomerce.ms.service.customer.application.query.CustomerQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.ecomerce.ms.service.customer.domain.shared.Constants.CUSTOMER_VERIFICATION_REPLY_TOPIC;
import static com.ecomerce.ms.service.customer.domain.shared.Constants.CUSTOMER_VERIFICATION_TOPIC;

@Component
@RequiredArgsConstructor
public class CustomerVerificationSagaListener {

    private final CustomerQueryHandler customerQueryHandler;
    private final KafkaTemplate<OrderSagaKey, CustomerVerificationReply> customerVerificationTemplate;

    @KafkaListener(topics = CUSTOMER_VERIFICATION_TOPIC)
    public void onCustomerVerificationEvent(@Payload CustomerVerificationEvent customerVerificationEvent,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderSagaKey sagaKey) {
        boolean satisfyOrderMakingCondition = customerQueryHandler.checkIfSatisfyMakingOrderCondition(customerVerificationEvent.getCustomerId());
        CustomerVerificationReply reply = CustomerVerificationReply.newBuilder()
                .setMetadata(buildMetadata())
                .setSagaInfo(customerVerificationEvent.getSagaInfo())
                .setSagaStepStatus(satisfyOrderMakingCondition ? SagaStepStatus.SUCCEEDED : SagaStepStatus.FAILED)
                .build();
        customerVerificationTemplate.send(CUSTOMER_VERIFICATION_REPLY_TOPIC, sagaKey, reply);
    }

    public Metadata buildMetadata() {
        return Metadata.newBuilder()
                .setSubmitTime(System.currentTimeMillis())
                .build();
    }
}
