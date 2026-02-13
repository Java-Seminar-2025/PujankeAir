package com.air.pujanke.model.entity;

import com.air.pujanke.model.custom.Seat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "`Ticket`",
        uniqueConstraints = {
            @UniqueConstraint(name = "seat_pick_unique", columnNames = {"flight_id", "seat_row", "seat_column"})
        }
      )
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", insertable = false, updatable = false, nullable = false)
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Embedded
    @NotNull
    private Seat seat;

    @Column(name = "ticket_holder_fullname", length = 40)
    private String ticketHolderFullName;

    @Column(name = "ticket_holder_pin", length = 11)
    private String ticketHolderPin;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal ticketPrice;

    @Column(name = "reservation_complete", nullable = false)
    private Boolean isReservationComplete;

    @Column(name = "generation_timestamp", insertable = false, updatable = false, nullable = false)
    private Instant generationTimestamp;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "ticket", fetch = FetchType.EAGER)
    private Set<AmenitiesEntity> amenities = new HashSet<>();
}
