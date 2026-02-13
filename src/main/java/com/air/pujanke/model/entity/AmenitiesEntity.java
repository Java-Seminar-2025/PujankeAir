package com.air.pujanke.model.entity;

import com.air.pujanke.model.custom.AmenitiesIdentity;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`Amenities`")
public class AmenitiesEntity {

    @EmbeddedId
    private AmenitiesIdentity amenitiesId;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @Column(nullable = false)
    private Integer quantity;
}
