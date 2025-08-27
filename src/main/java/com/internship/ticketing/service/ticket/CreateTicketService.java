package com.internship.ticketing.service.ticket;

import com.internship.ticketing.Command;
import com.internship.ticketing.dto.CreateTicketCommand;
import com.internship.ticketing.dto.TicketDTO;
import com.internship.ticketing.model.*;
import com.internship.ticketing.repository.EventRepository;
import com.internship.ticketing.repository.SeatLockRepository;
import com.internship.ticketing.repository.SeatRepository;
import com.internship.ticketing.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CreateTicketService implements Command<CreateTicketCommand, TicketDTO> {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final SeatLockRepository seatLockRepository;

    private final static int LOCK_TTL_MIN = 2;
    private static final Logger logger = LogManager.getLogger();

    public CreateTicketService(TicketRepository ticketRepository,
                               EventRepository eventRepository,
                               SeatRepository seatRepository,
                               SeatLockRepository seatLockRepository
                               ) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.seatLockRepository = seatLockRepository;
    }

    @Override
    public ResponseEntity<TicketDTO> execute(CreateTicketCommand command) {
        logger.info("Executing: {} command: {}", getClass(), command);
        Optional<Event> optionalEvent = eventRepository.findById(command.getEventId());
        Optional<Seat> optionalSeat = seatRepository.findById(command.getSeatId());
        // TODO: replace these with proper exceptions
        if (optionalEvent.isEmpty()) throw new RuntimeException("Event not found");
        if (optionalSeat.isEmpty()) throw new RuntimeException("Seat not found");

        Event event = optionalEvent.get();
        Seat seat = optionalSeat.get();

        // TODO: these ones too
        if (event.getEventState() != EventState.AVAILABLE)
            throw new RuntimeException("Event state not available");
        if (!seat.getLayoutId().equals(event.getLayoutId()))
            throw new RuntimeException("Seat does not belong to this event");

        LocalDateTime now = LocalDateTime.now();
        seatLockRepository.deleteExpired(now); // cleanup

        // try to lock the seat (unique (event_id, seat_id) must exist on seatlock)
        try {
            SeatLock lock = new SeatLock();
            lock.setEventId(command.getEventId());
            lock.setSeatId(command.getSeatId());
            lock.setCreatedAt(now);
            lock.setValidUntil(now.plusMinutes(LOCK_TTL_MIN));
            lock.setLockCode(java.util.UUID.randomUUID().toString());
            seatLockRepository.saveAndFlush(lock);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new RuntimeException("Seat is currently locked for this event"); // TODO: replace exception
        }

        // already sold
        if (ticketRepository.existsByEventIdAndSeatIdAndState(
                command.getEventId(), command.getSeatId(), TicketState.VALID)) {
            seatLockRepository.deleteByEventIdAndSeatId(command.getEventId(), command.getSeatId());
            throw new RuntimeException("Seat already sold for this event"); // TODO: replace exception
        }

        // create ticket
        try {
            Ticket t = new Ticket();
            t.setEventId(command.getEventId());
            t.setSeatId(command.getSeatId());
            t.setOwner(command.getOwner());
            t.setState(TicketState.VALID);
            t.setCreatedAt(now);
            t = ticketRepository.saveAndFlush(t);

            seatLockRepository.deleteByEventIdAndSeatId(command.getEventId(), command.getSeatId());
            return ResponseEntity.status(HttpStatus.CREATED).body(new TicketDTO(t));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            seatLockRepository.deleteByEventIdAndSeatId(command.getEventId(), command.getSeatId());
            throw new RuntimeException("Seat sold concurrently"); // TODO: replace exception
        }
    }

}
