package com.air.pujanke.model.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(@NotBlank(message = "Blank username") @Size(max = 20, message = "Too long username") String username,
                                  @NotBlank(message = "Blank password") @Size(max = 255, message = "Too long password") String passwordPlain,
                                  @NotBlank(message = "Password confirm blank") String passwordConfirmPlain)
{
    @AssertTrue(message = "Passwords do not match.")
    public boolean doesConfirmedPasswordMatch() {
        return passwordConfirmPlain != null && passwordConfirmPlain.equals(passwordPlain);
    }
}
