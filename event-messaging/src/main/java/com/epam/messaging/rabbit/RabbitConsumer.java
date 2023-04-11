package com.epam.messaging.rabbit;


import com.epam.dto.Event;
import com.epam.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("rabbit")
public class RabbitConsumer {
    private static final String createRequestTopic = "${topic.receive.create}";
    private static final String updateRequestTopic = "${topic.receive.update}";
    private static final String deleteRequestTopic = "${topic.receive.delete}";

    private static final String MESSAGE_CONSUME = "message consumed {}";

    private final EventService service;

    @Autowired
    public RabbitConsumer(EventService service) {
        this.service = service;
    }

    @RabbitListener(queues = createRequestTopic)
    public void receiveCreate(@Payload Event event) {
        log.info(MESSAGE_CONSUME, event);

        service.createEvent(event);
    }

    @RabbitListener(queues = updateRequestTopic)
    public void receiveUpdate(@Payload Event event) {
        log.info(MESSAGE_CONSUME, event);

        service.updateEvent(event.getEventId(), event);
    }

    @RabbitListener(queues = deleteRequestTopic)
    public void receivedDelete(@Payload Long id) {
        log.info(MESSAGE_CONSUME, id);

        service.deleteEvent(id);
    }
}

