package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
