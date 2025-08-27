package com.internship.ticketing.dto;

import com.internship.ticketing.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEventCommand {
    private Integer id;
    private Event event;

}
