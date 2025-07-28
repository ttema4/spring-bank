package com.example.storage.config;

import com.example.storage.dto.AccountCreatedEvent;
import com.example.storage.dto.AccountUpdatedEvent;
import com.example.storage.dto.UserCreatedEvent;
import com.example.storage.dto.UserUpdatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseProps(Class<?> targetType) {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "storage-group");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.storage.dto");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, targetType.getName());

        return props;
    }

    @Bean
    public ConsumerFactory<String, UserCreatedEvent> userCreatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps(UserCreatedEvent.class));
    }

    @Bean
    public ConsumerFactory<String, UserUpdatedEvent> userUpdatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps(UserUpdatedEvent.class));
    }

    @Bean
    public ConsumerFactory<String, AccountCreatedEvent> accountCreatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps(AccountCreatedEvent.class));
    }

    @Bean
    public ConsumerFactory<String, AccountUpdatedEvent> accountUpdatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseProps(AccountUpdatedEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> userCreatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>();
        factory.setConsumerFactory(userCreatedConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserUpdatedEvent> userUpdatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserUpdatedEvent>();
        factory.setConsumerFactory(userUpdatedConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent> accountCreatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent>();
        factory.setConsumerFactory(accountCreatedConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountUpdatedEvent> accountUpdatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, AccountUpdatedEvent>();
        factory.setConsumerFactory(accountUpdatedConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}