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

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListUpcomingEventsService implements Query<Void, List<EventDTO>> {

    private final EventRepository eventRepository;
    private static final Logger logger = LogManager.getLogger();

    public ListUpcomingEventsService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<List<EventDTO>> execute(Void input){
        logger.info("Executing {}", getClass());
        List<Event> events = eventRepository.findByStartDateAfter(LocalDateTime.now());
        List<EventDTO> eventDTOS = events.stream().map(EventDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(eventDTOS);
    }
}
