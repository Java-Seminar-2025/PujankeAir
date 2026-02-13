package com.air.pujanke.model.dto.flight;

import com.air.pujanke.model.dto.AircraftDto;
import com.air.pujanke.model.dto.PilotDto;
import com.air.pujanke.model.dto.airport.AirportReadDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record FlightReadDto(@NotNull Integer flightId,
                            AircraftDto aircraft,
                            @NotNull Integer estimatedDurationMinutes,
                            @NotNull LocalDate takeoffDate,
                            LocalTime takeoffTime,
                            AirportReadDto takeoffAirport,
                            AirportReadDto destinationAirport,
                            PilotDto pilot,
                            @NotNull @PositiveOrZero BigDecimal baseFare) {
}
