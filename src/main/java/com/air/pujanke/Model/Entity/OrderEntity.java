package com.air.pujanke.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    private Integer orderId;

    @Column(name = "order_uuid", nullable = false, insertable = false, updatable = false)
    private UUID orderUuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "order_finalized", insertable = false, nullable = false)
    private Boolean orderIsFinalized;

    @Column(name = "generation_timestamp",  insertable = false, updatable = false)
    private Instant generationTimestamp;

    @Column(name = "finalization_timestamp")
    private Instant finalizationTimestamp;
}
