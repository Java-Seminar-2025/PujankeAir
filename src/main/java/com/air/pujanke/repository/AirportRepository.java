package com.air.pujanke.repository;

import com.air.pujanke.model.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<AirportEntity, Integer> {
}
