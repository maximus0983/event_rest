package com.epam.messaging.config;


import com.epam.dto.Event;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration()
@Profile("kafka")
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ProducerFactory<String, Event> producerFactoryEvent() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Event> kafkaTemplateEvent() {
        return new KafkaTemplate<>(producerFactoryEvent());
    }

    @Bean
    public ProducerFactory<String, Long> producerFactoryLong() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Long> kafkaTemplateLong() {
        return new KafkaTemplate<>(producerFactoryLong());
    }

    @Bean
    public NewTopic topicCreate() {
        return TopicBuilder
                .name("create.event.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicUpdate() {
        return TopicBuilder
                .name("update.event.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicDelete() {
        return TopicBuilder
                .name("delete.event.notification")
                .partitions(1)
                .replicas(1)
                .build();
    }

    public ConsumerFactory<String, Event> userConsumerFactoryEvent() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(Event.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> userKafkaListenerContainerFactoryEvent() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactoryEvent());
        return factory;
    }

    public ConsumerFactory<String, Long> userConsumerFactoryLong() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new LongDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long> userKafkaListenerContainerFactoryLong() {
        ConcurrentKafkaListenerContainerFactory<String, Long> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactoryLong());
        return factory;
    }
}
