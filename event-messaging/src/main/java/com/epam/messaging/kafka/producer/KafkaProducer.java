package com.epam.messaging.kafka.producer;

import com.epam.dto.Event;
import com.epam.service.EventMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("kafka")
public class KafkaProducer implements EventMessaging {
    @Value("${topic.send.create}")
    private String eventCreate;

    @Value("${topic.send.update}")
    private String eventUpdate;

    @Value("${topic.send.delete}")
    private String eventDelete;

    private final KafkaTemplate<String, Event> kafkaTemplateEvent;
    private final KafkaTemplate<String, Long> kafkaTemplateLong;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Event> kafkaTemplateEvent, KafkaTemplate<String, Long> kafkaTemplateLong) {
        this.kafkaTemplateEvent = kafkaTemplateEvent;
        this.kafkaTemplateLong = kafkaTemplateLong;
    }

    public void sendMessage(Event event, String topic) {
        kafkaTemplateEvent.send(topic, event);

        log.info("produced {} to topic {}", event, topic);

    }

    @Override
    public void createEvent(Event event) {
        sendMessage(event, eventCreate);
    }

    @Override
    public void updateEvent(Event event) {
        sendMessage(event, eventUpdate);
    }

    @Override
    public void deleteEvent(Long id) {
        kafkaTemplateLong.send(eventDelete, id);
        log.info("produced {} to topic {}", id, eventDelete);
    }
}
