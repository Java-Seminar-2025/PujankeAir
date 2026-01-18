package com.air.pujanke.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Airport")
public class AirportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id", nullable = false, insertable = false, updatable = false)
    private Integer airportId;

    @Column(name = "airport_name", length = 40, nullable = false)
    private String airportName;

    @Column(name = "icao_code", length = 4, nullable = false, unique = true)
    private String icaoCode;

    @Column(length = 20, nullable = false)
    private String country;

    @Column(length = 20, nullable = false)
    private String city;
}
