package com.air.pujanke.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UserFundModificationDto(@NotNull @PositiveOrZero @Max(1000000) BigDecimal amount,
                                      @NotNull Boolean isAdd) {
}
