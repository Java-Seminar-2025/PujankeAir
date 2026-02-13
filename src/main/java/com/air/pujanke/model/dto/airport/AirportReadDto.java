package com.air.pujanke.model.dto.airport;

import com.air.pujanke.model.dto.CityDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AirportReadDto(@NotBlank @Size(max = 40) String airportName,
                             @NotBlank @Size(max = 4, min = 4) String icaoCode,
                             @NotNull CityDto city) {
}
