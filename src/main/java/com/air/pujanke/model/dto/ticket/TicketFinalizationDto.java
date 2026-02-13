package com.air.pujanke.model.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TicketFinalizationDto(@NotBlank String fullName,
                                    @NotBlank @Size(max = 11, min = 11) String pin) {
}
