package com.internship.ticketing.repository;

import com.internship.ticketing.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query(value = """
    SELECT s.* 
    FROM seat s
    JOIN event e ON e.layout_id = s.layout_id
    WHERE e.id = :eventId
  """, nativeQuery = true)
    List<Seat> findAllForEvent(@Param("eventId") Integer eventId);

    @Query(value = """
    SELECT s.*
    FROM seat s
    JOIN `event` e ON e.layout_id = s.layout_id
    WHERE e.id = :eventId
      AND NOT EXISTS (
        SELECT 1 FROM ticket t
        WHERE t.event_id = :eventId
          AND t.seat_id = s.id
          AND t.state = 'VALID'
      )
      AND NOT EXISTS (
        SELECT 1 FROM seat_lock l
        WHERE l.event_id = :eventId
          AND l.seat_id = s.id
          AND l.valid_until > NOW()
      )
  """, nativeQuery = true)
    List<Seat> findFreeForEvent(@Param("eventId") Integer eventId);
}
