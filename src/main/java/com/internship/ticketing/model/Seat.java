package com.internship.ticketing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "row")
    private String row;

    @Column(name = "number")
    private Integer number;

    @Column(name = "layout_id")
    private Integer layoutId;

    @OneToMany
    @JoinColumn(name = "seat_id")
    private List<SeatLock> seatLocks;

    @OneToMany
    @JoinColumn(name = "seat_id")
    private List<Ticket> tickets;
}
