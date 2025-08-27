package com.internship.ticketing.service.event;

import com.internship.ticketing.Command;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteEventService implements Command<Integer, Void> {

    private final EventRepository eventRepository;
    private static final Logger logger = LogManager.getLogger();

    public DeleteEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id) {
        logger.info("Executing: " + getClass() + " eventId: " + id);
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        // TODO: move error logs to in a proper exception
        logger.error("Failed: " + getClass() + " eventId: " + id);
        throw new RuntimeException("Event not found");
    }
}
