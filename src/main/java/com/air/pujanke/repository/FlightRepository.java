package com.air.pujanke.repository;

import com.air.pujanke.model.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {
}
