package com.internship.ticketing.service.event;

import com.internship.ticketing.Query;
import com.internship.ticketing.dto.EventDTO;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetEventByIdService implements Query<Integer, EventDTO> {

    private final EventRepository eventRepository;
    private static final Logger logger = LogManager.getLogger();
    public GetEventByIdService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<EventDTO> execute(Integer id) {
        logger.info("Executing:{} eventId:{}", getClass(), id);
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new EventDTO(optionalEvent.get()));
        }
        // TODO: implement appropriate exception
        throw new RuntimeException("Event not found");
    }
}
