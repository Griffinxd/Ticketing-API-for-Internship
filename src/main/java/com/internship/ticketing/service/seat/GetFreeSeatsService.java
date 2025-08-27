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
public class GetFreeSeatsService implements Query<Integer, List<SeatDTO>> {

    private final SeatRepository seatRepository;
    private final static Logger logger = LogManager.getLogger();
    public GetFreeSeatsService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public ResponseEntity<List<SeatDTO>> execute(Integer eventId) {
        logger.info("Executing: {} eventId: {}", getClass(), eventId);
        List<SeatDTO> list = seatRepository.findFreeForEvent(eventId)
                .stream()
                .map(SeatDTO::new)
                .toList();
        return ResponseEntity.ok(list);
    }
}
