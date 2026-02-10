package com.air.pujanke.repository;

import com.air.pujanke.model.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {
    List<FlightEntity> findAllByTakeoffDateBeforeAndTakeoffTimeBefore(LocalDate takeoffDateBefore, LocalTime takeoffTimeBefore);
}
