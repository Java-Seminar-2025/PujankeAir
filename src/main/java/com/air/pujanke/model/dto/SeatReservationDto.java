package com.air.pujanke.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SeatReservationDto(@NotBlank
                                 @Pattern(regexp = "^[1-9]\\d*[A-Z]$") String seatId) {
}
