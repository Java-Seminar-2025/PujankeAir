package com.air.pujanke.model.dto.flight;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record FlightModificationDto( Integer flightId,
                            Integer aircraftId,
                            @NotNull Integer estimatedDurationMinutes,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate takeoffDate,
                            @DateTimeFormat(pattern = "HH:mm") LocalTime takeoffTime,
                            @Size(max = 4, min = 4) String takeoffAirportIcao,
                            @Size(max = 4, min = 4) String destinationAirportIcao,
                            @Size(max = 11) String pilotPin,
                            @NotNull @PositiveOrZero BigDecimal baseFare) {
}
