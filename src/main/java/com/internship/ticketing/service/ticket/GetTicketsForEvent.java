package com.internship.ticketing.service.ticket;

import com.internship.ticketing.Query;
import com.internship.ticketing.dto.TicketDTO;
import com.internship.ticketing.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetTicketsForEvent implements Query<Integer, List<TicketDTO>> {

    private final TicketRepository ticketRepository;
    private static final Logger logger = LogManager.getLogger();
    public GetTicketsForEvent(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<List<TicketDTO>> execute(Integer eventId) {
        logger.info("Executing: {} eventID: {}", getClass(), eventId);
        var list = ticketRepository.findByEventId(eventId)
                .stream()
                .map(TicketDTO::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
