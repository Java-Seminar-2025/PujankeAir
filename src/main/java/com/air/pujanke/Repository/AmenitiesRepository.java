package com.air.pujanke.Repository;

import com.air.pujanke.Model.CompositeIdentity.AmenitiesIdentity;
import com.air.pujanke.Model.Entity.AmenitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenitiesRepository extends JpaRepository<AmenitiesEntity, AmenitiesIdentity> {
}
