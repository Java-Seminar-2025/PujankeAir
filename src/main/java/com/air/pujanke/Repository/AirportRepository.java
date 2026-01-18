package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<AirportEntity, Integer> {
}
