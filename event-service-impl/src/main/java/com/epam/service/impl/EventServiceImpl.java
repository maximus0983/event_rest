package com.epam.service.impl;

import com.epam.dto.Event;
import com.epam.service.EventMessaging;
import com.epam.service.EventService;
import com.epam.service.impl.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    EventRepo repo;

    @Autowired
    EventMessaging eventMessaging;

    @Override
    public Event createEvent(Event event) {
        Event save = repo.save(event);
        eventMessaging.createEvent(event);
        return save;
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<Event> optionalEvent = repo.findById(id);
        if (optionalEvent.isPresent()) {
            Event save = optionalEvent.get();
            save.setTitle(event.getTitle());
            save.setEventType(event.getEventType());
            save.setPlace(event.getPlace());
            save.setSpeaker(event.getSpeaker());
            save.setDateTime(event.getDateTime());
            eventMessaging.updateEvent(save);
            return repo.save(save);
        }
        return null;
    }

    @Override
    public Event getEvent(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteEvent(Long id) {
        eventMessaging.deleteEvent(id);
        repo.deleteById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return repo.findAll();
    }
}
