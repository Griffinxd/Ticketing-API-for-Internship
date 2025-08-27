package com.internship.ticketing.service.event;

import com.internship.ticketing.Command;
import com.internship.ticketing.dto.EventDTO;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateEventService implements Command<Event, EventDTO> {

    private final EventRepository eventRepository;

    private static final Logger logger = LogManager.getLogger();

    public CreateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(Event event){
        logger.info("Executing: " + getClass() + " eventId: " + event);
        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(new  EventDTO(savedEvent));
    }
}
