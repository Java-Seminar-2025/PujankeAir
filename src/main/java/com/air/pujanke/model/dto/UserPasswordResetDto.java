package com.air.pujanke.model.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordResetDto (@NotBlank @Size(max = 255) String currentPassword,
                                    @NotBlank @Size(max = 255) String newPassword,
                                    @NotBlank @Size(max = 255) String confirmNewPassword){

    @AssertTrue
    public boolean doesConfirmedPasswordMatch() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }
}
