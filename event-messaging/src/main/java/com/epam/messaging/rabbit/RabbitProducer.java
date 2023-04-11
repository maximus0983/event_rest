package com.epam.messaging.rabbit;

import com.epam.dto.Event;
import com.epam.service.EventMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("rabbit")
public class RabbitProducer implements EventMessaging {
    @Value("${topic.send.create}")
    private String eventCreate;

    @Value("${topic.send.update}")
    private String eventUpdate;

    @Value("${topic.send.delete}")
    private String eventDelete;

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createEvent(Event event) {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(eventCreate, event);
    }

    @Override
    public void updateEvent(Event event) {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(eventUpdate, event);
    }

    @Override
    public void deleteEvent(Long id) {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(eventDelete, id);
    }
}
