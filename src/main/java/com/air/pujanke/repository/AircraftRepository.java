package com.air.pujanke.repository;

import com.air.pujanke.model.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<AircraftEntity, Integer> {
}
