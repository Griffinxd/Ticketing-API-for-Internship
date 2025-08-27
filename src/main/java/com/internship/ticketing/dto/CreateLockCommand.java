package com.internship.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateLockCommand {
    private Integer eventId;
    private Integer seatId;
}
