package com.air.pujanke.model.dto.amenity;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AmenityDto(@NotNull Integer serviceId,
                         String serviceName,
                         BigDecimal serviceFee,
                         @Positive Integer quantity) {
}
