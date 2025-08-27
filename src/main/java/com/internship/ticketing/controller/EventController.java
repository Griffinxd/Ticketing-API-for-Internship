package com.internship.ticketing.controller;

import com.internship.ticketing.dto.EventDTO;
import com.internship.ticketing.dto.UpdateEventCommand;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.service.event.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    private final ListEventsService listEventsService;
    private final CreateEventService createEventService;
    private final UpdateEventService updateEventService;
    private final DeleteEventService deleteEventService;
    private final GetEventByIdService getEventByIdService;
    private final ListUpcomingEventsService listUpcomingEventsService;

    public EventController(ListEventsService listEventsService,
                           CreateEventService createEventService,
                           UpdateEventService updateEventService,
                           DeleteEventService deleteEventService,
                           GetEventByIdService getEventByIdService,
                           ListUpcomingEventsService listUpcomingEventsService) {
        this.listEventsService = listEventsService;
        this.createEventService = createEventService;
        this.updateEventService = updateEventService;
        this.deleteEventService = deleteEventService;
        this.getEventByIdService = getEventByIdService;
        this.listUpcomingEventsService = listUpcomingEventsService;
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody Event event){
        return createEventService.execute(event);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> listEvents(){
        return listEventsService.execute(null);
    }

    @GetMapping("/events/upcoming")
    public ResponseEntity<List<EventDTO>> listUpcomingEvents(){
        return listUpcomingEventsService.execute(null);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Integer id){
        return getEventByIdService.execute(id);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id, @RequestBody Event event){
        return updateEventService.execute(new UpdateEventCommand(id, event));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id){
        return deleteEventService.execute(id);
    }
}
