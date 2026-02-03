package com.air.pujanke.model.compositeidentity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AmenitiesIdentity implements Serializable {

    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;

    @Column(name = "service_id", nullable = false)
    private Integer serviceId;
}
