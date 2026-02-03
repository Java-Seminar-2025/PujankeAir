package com.air.pujanke.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Aircraft")
public class AircraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id", nullable = false, insertable = false, updatable = false)
    private Integer aircraftId;

    @Column(name = "manufacturer", length = 20)
    private String manufacturerName;

    @Column(name = "model", length = 10)
    private String modelName;

    @Column(name = "serial_number", length = 10, nullable = false, unique = true)
    private String serialNumber;
    
    @Column(name = "seat_rows", nullable = false)
    private Integer seatRowCount;

    @Column(name = "seat_columns", nullable = false)
    private Integer seatColumnCount;
}
