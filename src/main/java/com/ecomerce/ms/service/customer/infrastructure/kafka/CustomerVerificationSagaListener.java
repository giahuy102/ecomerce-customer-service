package com.ecomerce.ms.service.customer.infrastructure.kafka;

import com.ecomerce.ms.service.CustomerVerificationCommand;
import com.ecomerce.ms.service.CustomerVerificationReply;
import com.ecomerce.ms.service.OrderingSagaKey;
import com.ecomerce.ms.service.SagaStepStatusMessage;
import com.ecomerce.ms.service.customer.application.query.CustomerQueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.ecomerce.ms.service.customer.domain.shared.Constants.CUSTOMER_VERIFICATION_REPLY_TOPIC;
import static com.ecomerce.ms.service.customer.domain.shared.Constants.CUSTOMER_VERIFICATION_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerVerificationSagaListener {

    private final CustomerQueryHandler customerQueryHandler;
    private final KafkaTemplate<OrderingSagaKey, CustomerVerificationReply> customerVerificationTemplate;

    @KafkaListener(topics = CUSTOMER_VERIFICATION_TOPIC)
    public void onCustomerVerificationEvent(@Payload CustomerVerificationCommand customerVerificationCommand,
                                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) OrderingSagaKey sagaKey) {
        boolean satisfyOrderMakingCondition = customerQueryHandler.checkIfSatisfyMakingOrderCondition(customerVerificationCommand.getCustomerId());
        CustomerVerificationReply reply = CustomerVerificationReply.newBuilder()
                .setSagaMetadata(customerVerificationCommand.getSagaMetadata())
                .setSagaStepStatus(satisfyOrderMakingCondition ? SagaStepStatusMessage.SUCCEEDED : SagaStepStatusMessage.FAILED)
                .build();
        customerVerificationTemplate.send(CUSTOMER_VERIFICATION_REPLY_TOPIC, sagaKey, reply)
                .addCallback(new ListenableFutureCallback<SendResult<OrderingSagaKey, CustomerVerificationReply>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=[\"{}\"] due to : {}", reply, ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<OrderingSagaKey, CustomerVerificationReply> result) {
                        log.info("Sent message=[\"{}\"] with offset=[\"{}\"]", reply, result.getRecordMetadata().offset());
                    }
                });
    }
}
