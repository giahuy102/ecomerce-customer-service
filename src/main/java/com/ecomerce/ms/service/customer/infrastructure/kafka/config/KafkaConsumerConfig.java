package com.ecomerce.ms.service.customer.infrastructure.kafka.config;

import com.ecomerce.ms.service.CustomerVerificationEvent;
import com.ecomerce.ms.service.OrderSagaKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public <K, V> ConsumerFactory<K, V> consumerFactory() {
        Map<String, Object> configMap = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<OrderSagaKey, CustomerVerificationEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<OrderSagaKey, CustomerVerificationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
