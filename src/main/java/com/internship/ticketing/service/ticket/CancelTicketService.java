package com.internship.ticketing.service.ticket;

import com.internship.ticketing.Command;
import com.internship.ticketing.dto.TicketDTO;
import com.internship.ticketing.model.Ticket;
import com.internship.ticketing.model.TicketState;
import com.internship.ticketing.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelTicketService implements Command<Integer, TicketDTO> {

    private final TicketRepository ticketRepository;
    private static final Logger logger = LogManager.getLogger();
    public CancelTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ResponseEntity<TicketDTO> execute(Integer ticketId) {
        logger.info("Executing {} ticketId: {}", getClass(), ticketId);
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found: " + ticketId)); // TODO: proper exception

        if (ticket.getState() != TicketState.CANCELLED) {
            ticket.setState(TicketState.CANCELLED);
            ticket = ticketRepository.saveAndFlush(ticket);
        }

        return ResponseEntity.ok(new TicketDTO(ticket));
    }
}
