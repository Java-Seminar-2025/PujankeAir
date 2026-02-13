package com.air.pujanke.model.custom;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AmenitiesIdentity implements Serializable {

    @Column(name = "ticket_id", nullable = false, updatable = false)
    private Integer ticketId;

    @Column(name = "service_id", nullable = false, updatable = false)
    private Integer serviceId;
}
