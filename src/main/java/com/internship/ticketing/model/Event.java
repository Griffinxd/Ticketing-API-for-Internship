package com.internship.ticketing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventState eventState;

    @Column(name = "date")
    private LocalDateTime startDate;

    @Column(name = "layout_id")
    private Integer layoutId;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<SeatLock> seatLocks;

    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Ticket> tickets;
}
