package com.air.pujanke.repository;

import com.air.pujanke.model.compositeidentity.AmenitiesIdentity;
import com.air.pujanke.model.entity.AmenitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenitiesRepository extends JpaRepository<AmenitiesEntity, AmenitiesIdentity> {
}
