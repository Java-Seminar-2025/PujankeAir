package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {
}
