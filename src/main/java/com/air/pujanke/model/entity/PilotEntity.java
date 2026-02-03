package com.air.pujanke.model.entity;


import com.air.pujanke.model.enums.Rank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pilot")
public class PilotEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pilot_id", insertable = false, updatable = false, nullable = false)
    private Integer pilotId;

    @Column(name = "full_name", length = 40, nullable = false)
    private String fullName;

    @Column(length = 11, nullable = false)
    private String pin;

    @Column(name = "flight_hours")
    private Integer flightHours;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Column(name = "date_employed")
    private Date dateEmployed;
}
