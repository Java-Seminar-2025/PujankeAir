package com.air.pujanke.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Flight")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false, insertable = false, updatable = false)
    private Integer flightId;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private AircraftEntity aircraft;

    @Column(name = "estimated_duration_min")
    private Integer estimatedDurationMinutes;

    @Column(name = "takeoff_date", nullable = false)
    private Date takeoffDate;

    @Column(name = "takeoff_time")
    private Timestamp takeoffTime;

    @ManyToOne
    @JoinColumn(name = "takeoff_airport_id")
    private AirportEntity takeoffAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id")
    private AirportEntity destinationAirport;

    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private PilotEntity pilot;

    @Column(name = "base_fare", precision = 10, scale = 2)
    private BigDecimal baseFare;
}
