package com.epam.rest.controller;

import com.epam.dto.Event;
import com.epam.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    EventService eventService;

    @PostMapping("/create")
    public Event create(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAllEvents();
    }
}
