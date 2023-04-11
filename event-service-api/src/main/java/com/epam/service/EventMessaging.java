package com.epam.service;

import com.epam.dto.Event;

public interface EventMessaging {
    void createEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(Long id);
}
