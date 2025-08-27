package com.internship.ticketing.controller;

import com.internship.ticketing.dto.CreateLockCommand;
import com.internship.ticketing.dto.SeatDTO;
import com.internship.ticketing.service.seat.GetFreeSeatsService;
import com.internship.ticketing.service.seat.GetSeatsService;
import com.internship.ticketing.service.seat.LockSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatController {
    private final GetSeatsService getSeatsService;
    private final GetFreeSeatsService  getFreeSeatsService;
    private final LockSeatService lockSeatService;

    public SeatController(GetSeatsService getSeatsService,
                          GetFreeSeatsService getFreeSeatsService,
                          LockSeatService lockSeatService) {
        this.getSeatsService = getSeatsService;
        this.getFreeSeatsService = getFreeSeatsService;
        this.lockSeatService = lockSeatService;
    }

    @GetMapping("/events/{eventId}/seats/free")
    public ResponseEntity<List<SeatDTO>> getFreeSeats(@PathVariable Integer eventId){
        return getFreeSeatsService.execute(eventId);
    }

    @GetMapping("/events/{eventId}/seats")
    public ResponseEntity<List<SeatDTO>> getSeats(@PathVariable Integer eventId){
        return getSeatsService.execute(eventId);
    }

    @PostMapping("/events/{eventId}/seats/{seatId}/lock")
    public ResponseEntity<Void> lockSeat(@PathVariable Integer eventId, @PathVariable Integer seatId){
        return lockSeatService.execute(new CreateLockCommand(eventId, seatId));
    }

}
