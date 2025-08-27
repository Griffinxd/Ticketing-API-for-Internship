package com.internship.ticketing.service.event;

import com.internship.ticketing.Query;
import com.internship.ticketing.model.Event;
import com.internship.ticketing.repository.EventRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEventsService implements Query<Void, List<Event>> {

    private final EventRepository eventRepository;
    private final static Logger logger = LogManager.getLogger();

    public ListEventsService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<List<Event>> execute(Void input){
        logger.info("Executing: {}", getClass());
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

}
