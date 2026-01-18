package com.air.pujanke.Repository;

import com.air.pujanke.Model.Entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
}
