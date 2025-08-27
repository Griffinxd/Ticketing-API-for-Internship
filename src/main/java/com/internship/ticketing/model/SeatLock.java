package com.internship.ticketing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(
        name = "seatlock",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seat_id"})
)
public class SeatLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "lock_code")
    private String lockCode;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "seat_id")
    private Integer seatId;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (validUntil == null) validUntil = createdAt.plusMinutes(2);
        if (lockCode == null) lockCode = java.util.UUID.randomUUID().toString();
    }
}
