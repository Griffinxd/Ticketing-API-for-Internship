// TicketRepository.java
package com.internship.ticketing.repository;

import com.internship.ticketing.model.Ticket;
import com.internship.ticketing.model.TicketState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByEventId(Integer eventId);
    boolean existsByEventIdAndSeatIdAndState(Integer eventId, Integer seatId, TicketState state);
}
