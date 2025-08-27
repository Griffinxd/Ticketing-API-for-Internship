package com.internship.ticketing.dto;

import com.internship.ticketing.model.Event;
import com.internship.ticketing.model.EventState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String name;
    private Integer id;
    private EventState eventState;
    private LocalDateTime startDate;
    private Integer layoutId;

    public EventDTO(Event event){
        this.id = event.getId();
        this.name = event.getName();
        this.eventState = event.getEventState();
        this.startDate = event.getStartDate();
        this.layoutId = event.getLayoutId();
    }
}
