package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record UserManagementDto(@NotBlank String username,
                                @NotNull Instant registrationTimestamp,
                                @NotNull Boolean isEnabled) {
}
