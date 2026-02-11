package com.air.pujanke.model.dto;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record FlightSearchFormDto(LocalDate takeoffDate,
                                  String originAirportIcao,
                                  String destinationAirportIcao,
                                  @Positive BigDecimal baseFareLesserThan) {
}
