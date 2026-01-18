package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<AircraftEntity, Integer> {
}
