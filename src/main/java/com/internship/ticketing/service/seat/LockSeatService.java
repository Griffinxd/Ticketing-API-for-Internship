package com.internship.ticketing.service.seat;

import com.internship.ticketing.Command;
import com.internship.ticketing.dto.CreateLockCommand;
import com.internship.ticketing.model.SeatLock;
import com.internship.ticketing.repository.SeatLockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LockSeatService implements Command<CreateLockCommand, Void> {

    private final SeatLockRepository seatLockRepository;

    private static final int LOCK_TTL_MIN = 2;
    private static final Logger logger = LogManager.getLogger();

    public LockSeatService(SeatLockRepository seatLockRepository) {
        this.seatLockRepository = seatLockRepository;
    }

    @Transactional
    public ResponseEntity<Void> execute(CreateLockCommand command){
        logger.info("Executing {} command: {}", getClass(), command);
        LocalDateTime now = LocalDateTime.now();
        seatLockRepository.deleteExpired(now);
        Optional<SeatLock> optionalSeatLock = seatLockRepository.findByEventIdAndSeatId(command.getEventId(), command.getSeatId());
        if (optionalSeatLock.isPresent()){
            logger.error("Error: Seat is Already Locked - {}", command);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        SeatLock seatLock = new SeatLock();
        seatLock.setSeatId(command.getSeatId());
        seatLock.setEventId(command.getEventId());
        seatLock.setCreatedAt(now);
        seatLock.setValidUntil(now.plusMinutes(LOCK_TTL_MIN));
        logger.info("Seat is locked for {} minutes at {}", LOCK_TTL_MIN, now);
        try{
            seatLockRepository.save(seatLock);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (DataIntegrityViolationException ex){
            /* TODO: exception */
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
