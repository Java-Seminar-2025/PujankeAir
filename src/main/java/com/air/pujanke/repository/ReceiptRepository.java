package com.air.pujanke.repository;

import com.air.pujanke.model.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Integer> {
}
