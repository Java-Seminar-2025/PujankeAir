package com.air.pujanke.repository;

import com.air.pujanke.model.entity.PilotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<PilotEntity, Integer> {
}
