package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.PilotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<PilotEntity, Integer> {
}
