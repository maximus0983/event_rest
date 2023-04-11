package com.epam.messaging.activemq;

import com.epam.dto.Event;
import com.epam.service.EventMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("activemq")
public class ActiveProducer implements EventMessaging {
    @Value("${topic.send.create}")
    private String eventCreate;

    @Value("${topic.send.update}")
    private String eventUpdate;

    @Value("${topic.send.delete}")
    private String eventDelete;

    @Autowired
    JmsTemplate template;

    @Override
    public void createEvent(Event event) {
        log.info("Sending message...");
        template.convertAndSend(eventCreate, event);
    }

    @Override
    public void updateEvent(Event event) {
        log.info("Sending message...");
        template.convertAndSend(eventUpdate, event);
    }

    @Override
    public void deleteEvent(Long id) {
        log.info("Sending message...");
        template.convertAndSend(eventDelete, id);
    }
}
