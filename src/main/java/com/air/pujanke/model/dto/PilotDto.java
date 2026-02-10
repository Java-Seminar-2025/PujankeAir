package com.air.pujanke.model.dto;

import com.air.pujanke.model.enums.Rank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PilotDto(@NotBlank @Size(max = 40) String fullName,
                       @NotBlank @Size(max = 11) String pin,
                       Rank rank) {
}
