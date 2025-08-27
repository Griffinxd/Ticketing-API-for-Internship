package com.internship.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateTicketCommand{
    private Integer eventId;
    private Integer seatId;
    private String owner;
}
