package com.air.pujanke.service.validator;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.UserAccountDeletionDto;
import com.air.pujanke.model.dto.UserFundModificationDto;
import com.air.pujanke.model.dto.UserPasswordResetDto;
import com.air.pujanke.model.dto.UserRegistrationDto;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountOperationValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateRegistration(@NonNull UserRegistrationDto userDto) {
        if (!userDto.passwordPlain().equals(userDto.passwordConfirmPlain()))
            throw new InvalidArgumentException("Passwords do not match.");
        else if (userRepository.existsByUsername(userDto.username()))
            throw new InvalidArgumentException("Username already taken.");
    }

    public void validateFundModification(UserEntity user, @NonNull UserFundModificationDto fundDto) {
        if (fundDto.amount().signum() < 0)
            throw new InvalidArgumentException("The amount of funds to be added or removed must not be negative.");
        else if (user.getFunds().compareTo(fundDto.amount()) < 0 && !fundDto.isAdd())
            throw new InvalidArgumentException("Insufficient funds");
    }

    public void validatePasswordReset(UserEntity user, UserPasswordResetDto passwordResetDto) {
        if (!passwordEncoder.matches(passwordResetDto.currentPassword(), user.getPasswordHash()))
            throw new InvalidArgumentException("Incorrect password.");
    }

    public void validateAccountDeletion(UserEntity user, UserAccountDeletionDto accountDeletionDto) {
        if (!passwordEncoder.matches(accountDeletionDto.password(), user.getPasswordHash()))
            throw new InvalidArgumentException("Incorrect password.");
    }
}
