package com.internship.ticketing.dto;

import com.internship.ticketing.model.Seat;
import lombok.Data;

@Data
public class SeatDTO {
    private Integer id;
    private String row;
    private Integer number;
    private Integer layoutId;

    public SeatDTO(Seat seat){
        this.id = seat.getId();
        this.row = seat.getRow();
        this.number = seat.getNumber();
        this.layoutId = seat.getLayoutId();
    }
}
