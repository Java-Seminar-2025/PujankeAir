package com.air.pujanke.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", insertable = false, updatable = false, nullable = false)
    private Integer serviceId;

    @Column(name = "service_name", length = 30, nullable = false)
    private String serviceName;

    @Column(name = "service_fee", precision = 10, scale = 2)
    private BigDecimal serviceFee;
}
