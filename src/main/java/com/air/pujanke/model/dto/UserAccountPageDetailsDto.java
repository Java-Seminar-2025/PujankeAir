package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record UserAccountPageDetailsDto(@NotBlank String username,
                                        @NotNull BigDecimal funds,
                                        @NotNull Instant registrationTimestamp) {
}
