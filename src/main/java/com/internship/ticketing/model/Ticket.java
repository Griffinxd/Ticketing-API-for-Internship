package com.internship.ticketing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private TicketState state;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "seat_id")
    private Integer seatId;
}
