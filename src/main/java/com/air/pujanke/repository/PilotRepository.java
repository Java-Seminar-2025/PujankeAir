package com.air.pujanke.repository;

import com.air.pujanke.model.entity.PilotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PilotRepository extends JpaRepository<PilotEntity, Integer> {
    boolean existsByPin(String pin);

    Optional<PilotEntity> findByPin(String pin);
}
