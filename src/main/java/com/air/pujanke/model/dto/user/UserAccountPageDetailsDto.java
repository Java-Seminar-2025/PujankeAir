package com.air.pujanke.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record UserAccountPageDetailsDto(@NotBlank String username,
                                        @NotNull @PositiveOrZero BigDecimal funds,
                                        @NotNull Instant registrationTimestamp) {
}
