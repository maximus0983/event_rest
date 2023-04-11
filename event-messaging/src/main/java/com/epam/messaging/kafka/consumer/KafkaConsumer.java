package com.epam.messaging.kafka.consumer;

import com.epam.dto.Event;
import com.epam.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("kafka")
public class KafkaConsumer {
    private static final String createRequestTopic = "${topic.receive.create}";
    private static final String updateRequestTopic = "${topic.receive.update}";
    private static final String deleteRequestTopic = "${topic.receive.delete}";

    private static final String eventDelete="${topic.send.delete}";
    private static final String MESSAGE_CONSUME = "message consumed {}";

    private final EventService service;

    @Autowired
    public KafkaConsumer(EventService service) {
        this.service = service;
    }

    @KafkaListener(topics = createRequestTopic, containerFactory = "userKafkaListenerContainerFactoryEvent")
    public void consumeAndCreateMessage(Event event) {
        log.info(MESSAGE_CONSUME, event);

        service.createEvent(event);
    }

    @KafkaListener(topics = updateRequestTopic, containerFactory = "userKafkaListenerContainerFactoryEvent")
    public void consumeAndUpdateMessage(Event event) {
        log.info(MESSAGE_CONSUME, event);

        service.updateEvent(event.getEventId(), event);
    }

    @KafkaListener(topics = deleteRequestTopic, containerFactory = "userKafkaListenerContainerFactoryLong")
    public void consumeAndDeleteMessage(Long id) {
        log.info(MESSAGE_CONSUME, id);

        service.deleteEvent(id);
    }
}
