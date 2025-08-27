package com.internship.ticketing.controller;

import com.internship.ticketing.dto.CreateTicketCommand;
import com.internship.ticketing.dto.TicketDTO;
import com.internship.ticketing.service.ticket.CancelTicketService;
import com.internship.ticketing.service.ticket.CreateTicketService;
import com.internship.ticketing.service.ticket.GetTicketsForEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final CreateTicketService createTicketService;
    private final GetTicketsForEvent getTicketsForEvent;
    private final CancelTicketService cancelTicketService;

    public TicketController(CreateTicketService createTicketService,
                            GetTicketsForEvent getTicketsForEvent,
                            CancelTicketService cancelTicketService) {
        this.createTicketService = createTicketService;
        this.getTicketsForEvent = getTicketsForEvent;
        this.cancelTicketService = cancelTicketService;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody CreateTicketCommand command) {
        return createTicketService.execute(command);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<TicketDTO>> getTicketsForEvent(@PathVariable Integer eventId) {
        return getTicketsForEvent.execute(eventId);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> cancelTicket(@PathVariable Integer ticketId) {
        return cancelTicketService.execute(ticketId);
    }
}
