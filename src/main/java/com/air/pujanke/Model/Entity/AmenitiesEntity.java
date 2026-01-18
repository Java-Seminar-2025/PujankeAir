package com.air.pujanke.Model.Entity;

import com.air.pujanke.Model.CompositeIdentity.AmenitiesIdentity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Amenities")
public class AmenitiesEntity {

    @EmbeddedId
    private AmenitiesIdentity amenitiesId;

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
