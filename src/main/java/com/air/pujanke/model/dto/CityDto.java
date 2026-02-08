package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityDto(@NotNull Integer zipcode,
                      @NotBlank String cityName,
                      @NotBlank String countryName) {
}
