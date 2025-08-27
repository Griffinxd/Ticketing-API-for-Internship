package com.internship.ticketing.repository;

import com.internship.ticketing.model.SeatLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SeatLockRepository extends JpaRepository<SeatLock, Integer> {

    @Modifying
    @Query("DELETE FROM SeatLock l WHERE l.validUntil < :now")
    int deleteExpired(@Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM SeatLock l WHERE l.eventId = :eventId AND l.seatId = :seatId")
    int deleteByEventIdAndSeatId(@Param("eventId") Integer eventId,
                                 @Param("seatId") Integer seatId);

    Optional<SeatLock> findByEventIdAndSeatId(Integer eventId, Integer seatId);
}
