package com.internship.ticketing.service.seat;

import com.internship.ticketing.Query;
import com.internship.ticketing.dto.SeatDTO;
import com.internship.ticketing.repository.SeatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSeatsService implements Query<Integer, List<SeatDTO>> {

    private final SeatRepository seatRepository;
    private static final Logger logger = LogManager.getLogger();
    public GetSeatsService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    @Override
    public ResponseEntity<List<SeatDTO>> execute(Integer eventId) {
        logger.info("Executing {} eventId: {}", getClass(), eventId);
        List<SeatDTO> list = seatRepository.findAllForEvent(eventId).stream()
                .map(SeatDTO::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
