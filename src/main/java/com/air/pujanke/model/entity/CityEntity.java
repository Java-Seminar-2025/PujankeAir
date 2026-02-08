package com.air.pujanke.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`City`")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zipcode", nullable = false, insertable = false, updatable = false)
    private Integer zipcode;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "country_name", nullable = false)
    private String countryName;
}
