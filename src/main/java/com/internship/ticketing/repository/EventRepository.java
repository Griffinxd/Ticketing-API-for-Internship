package com.internship.ticketing.repository;

import com.internship.ticketing.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByStartDateAfter(LocalDateTime dateTime);
}
