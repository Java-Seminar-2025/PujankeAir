package com.air.pujanke.model.entity;

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
@Table(name = "`Receipt`")
public class ReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id", nullable = false, insertable = false, updatable = false)
    private Integer receiptId;

    @Column(name = "receipt_uuid", nullable = false, insertable = false, updatable = false)
    private UUID receiptUuid;

    @OneToOne
    @JoinColumn(name = "ticket_id", unique = true, nullable = false, updatable = false)
    private TicketEntity ticket;

    @Column(name = "total_price", precision = 10, scale = 2, updatable = false)
    private BigDecimal totalPrice;

    @Column(name = "generation_timestamp",  insertable = false, updatable = false)
    private Instant generationTimestamp;
}
