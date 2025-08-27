package com.internship.ticketing.dto;

import com.internship.ticketing.model.Ticket;
import com.internship.ticketing.model.TicketState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data @AllArgsConstructor @NoArgsConstructor
public class TicketDTO {
    private Integer id;
    private Integer eventId;
    private Integer seatId;
    private String owner;
    private TicketState state;
    private Instant createdAt;

    public TicketDTO(Ticket ticket){
        this.id = ticket.getId();
        this.eventId = ticket.getEventId();
        this.seatId = ticket.getSeatId();
        this.owner = ticket.getOwner();
        this.state = ticket.getState();
        this.createdAt = Instant.now();
    }

}
