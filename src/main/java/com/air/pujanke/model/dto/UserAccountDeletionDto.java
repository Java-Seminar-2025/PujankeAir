package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAccountDeletionDto(@NotBlank @Size(max = 255) String password) {
}
