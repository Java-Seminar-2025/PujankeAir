package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AircraftDto(@NotNull Integer aircraftId,
                          @Size(max = 20) String manufacturerName,
                          @Size(max = 10) String modelName,
                          @NotNull @Positive Integer seatRowCount,
                          @NotNull @Positive Integer seatColumnCount) {
}
