package com.air.pujanke.repository;

import com.air.pujanke.model.entity.FlightEntity;
import com.air.pujanke.model.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
    boolean existsByFlight(FlightEntity flight);
}
