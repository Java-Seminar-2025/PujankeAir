package com.air.pujanke.repository;

import com.air.pujanke.model.entity.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer>, JpaSpecificationExecutor<FlightEntity> {
    List<FlightEntity> findAllByTakeoffDateBeforeAndTakeoffTimeBefore(LocalDate takeoffDateBefore, LocalTime takeoffTimeBefore);
}
