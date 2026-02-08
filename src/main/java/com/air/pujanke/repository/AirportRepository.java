package com.air.pujanke.repository;

import com.air.pujanke.model.entity.AirportEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirportEntity, Integer> {

    public void deleteByIcaoCode(@NotBlank String icaoCode);

    boolean existsByIcaoCode(String icaoCode);

    Optional<AirportEntity> findByIcaoCode(String icaoCode);
}
