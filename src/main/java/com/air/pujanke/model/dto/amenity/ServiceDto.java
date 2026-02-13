package com.air.pujanke.model.dto.amenity;

import java.math.BigDecimal;

public record ServiceDto(Integer serviceId,
                         String serviceName,
                         BigDecimal serviceFee) {
}
