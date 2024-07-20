package com.ecomerce.ms.service.customer.infrastructure.kafka.config;


import com.ecomerce.ms.service.CustomerVerificationReply;
import com.ecomerce.ms.service.OrderSagaKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public <K, V> ProducerFactory<K, V> producerFactory() {
        final Map<String, Object> configMap = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(configMap);
    }

    @Bean
    public KafkaTemplate<OrderSagaKey, CustomerVerificationReply> customerVerificationReplyTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
