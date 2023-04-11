package com.epam.messaging.activemq;

import com.epam.dto.Event;
import com.epam.service.EventService;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("activemq")
public class ActiveConsumer {
    private static final String createRequestTopic = "${topic.receive.create}";
    private static final String updateRequestTopic = "${topic.receive.update}";
    private static final String deleteRequestTopic = "${topic.receive.delete}";

    private static final String MESSAGE_CONSUME = "message consumed {}";

    private final EventService service;

    @Autowired
    public ActiveConsumer(EventService service) {
        this.service = service;
    }

    @JmsListener(destination = createRequestTopic,containerFactory = "myFactory")
    public void receiveCreate(final Event event) {
        try {
//            ObjectMessage objectMessage = (ObjectMessage) message;
//            Event event = (Event) objectMessage.getObject();
            service.createEvent(event);
            log.info("consumed: {}", event);
        } catch (Exception e) {
            log.error("Received Exception : " + e);
        }
    }

    @JmsListener(destination = updateRequestTopic,containerFactory = "myFactory")
    public void receiveUpd(final Event event) {
        try {
//            ObjectMessage objectMessage = (ObjectMessage) message.;
//            Event event = (Event) objectMessage.getObject();
            service.updateEvent(event.getEventId(), event);
            log.info("consumed: {}", event);
        } catch (Exception e) {
            log.error("Received Exception : " + e);
        }
    }

    @JmsListener(destination = deleteRequestTopic)
    public void receiveDel(final Long id) {
        try {
//            ObjectMessage objectMessage = (ObjectMessage) message;
//            Long id = (Long) objectMessage.getObject();
            service.deleteEvent(id);
            log.info("consumed: {}", id);
        } catch (Exception e) {
            log.error("Received Exception : " + e);
        }
    }
}
