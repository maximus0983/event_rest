package com.epam.messaging.config;

import com.epam.dto.Event;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableJms
@Configuration
@Profile("activemq")
public class ActiveMQConfig {
    @Value("${topic.send.create}")
    private String eventCreate;

    @Value("${topic.send.update}")
    private String eventUpdate;

    @Value("${topic.send.delete}")
    private String eventDelete;

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    //Initialize queues
    @Bean(name = "eventCreateDestination")
    public Destination eventCreate() {
        return new ActiveMQQueue(eventCreate);
    }

    @Bean(name = "eventUpdateDestination")
    public Destination eventUpdate() {
        return new ActiveMQQueue(eventUpdate);
    }

    @Bean(name = "eventDeleteDestination")
    public Destination eventDelete() {
        return new ActiveMQQueue(eventDelete);
    }
}
