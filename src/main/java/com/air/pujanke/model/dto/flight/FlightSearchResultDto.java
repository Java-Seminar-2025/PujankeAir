package com.air.pujanke.model.dto.flight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record FlightSearchResultDto(Integer flightId,
                                    LocalDate takeoffDate,
                                    LocalTime takeoffTime,
                                    String originAirportName,
                                    String originAirportIcao,
                                    String originAirportCityName,
                                    String destinationAirportName,
                                    String destinationAirportIcao,
                                    String destinationAirportCityName,
                                    BigDecimal baseFare) {
}
