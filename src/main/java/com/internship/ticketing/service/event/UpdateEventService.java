package com.internship.ticketing.service.event;

import com.internship.ticketing.Command;
import com.internship.ticketing.dto.EventDTO;
import com.internship.ticketing.dto.UpdateEventCommand;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateEventService implements Command<UpdateEventCommand, EventDTO> {

    private final EventRepository eventRepository;
    private final static Logger logger = LogManager.getLogger();

    public UpdateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(UpdateEventCommand command) {
        logger.info("Executing {} command: {}", getClass(), command);
        Optional<Event> optionalEvent = eventRepository.findById(command.getId());

        if (optionalEvent.isPresent()) {
            Event event = command.getEvent();
            event.setId(command.getId());
            eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.OK).body(new EventDTO(event));
        }

        throw new RuntimeException("Event not found");
    }
}
