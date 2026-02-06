package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.UserNotFoundException;
import com.air.pujanke.model.dto.*;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.UserRepository;
import com.air.pujanke.utility.ConfiguredObjectMapper;
import com.air.pujanke.service.validator.UserAccountOperationValidator;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountOperationValidator userAccountOpValidator;

    public boolean isUsernameTaken(@NonNull String username) {
        return userRepository.existsByUsername(username);
    }

    public void createUser(UserRegistrationDto userDto) {
        userAccountOpValidator.validateRegistration(userDto);
        var user = ConfiguredObjectMapper.getMapper().convertValue(userDto, UserEntity.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.passwordPlain()));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public UserAccountPageDetailsDto fetchUserAccountPageDetails(@NonNull String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        return ConfiguredObjectMapper.getMapper().convertValue(user, UserAccountPageDetailsDto.class);
    }

    @Transactional
    public void modifyUserFunds(@NonNull String username, UserFundModificationDto fundDto) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        userAccountOpValidator.validateFundModification(user, fundDto);
        BigDecimal newAmount;

        if (!fundDto.isAdd())
            newAmount = user.getFunds().subtract(fundDto.amount());
        else
            newAmount = user.getFunds().add(fundDto.amount());

        user.setFunds(newAmount);
    }

    @Transactional
    public void resetPassword(@NonNull String username, UserPasswordResetDto passwordResetDto) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        userAccountOpValidator.validatePasswordReset(user, passwordResetDto);
        user.setPasswordHash(passwordEncoder.encode(passwordResetDto.newPassword()));
    }

    @Transactional
    public void deleteAccount(@NonNull String username, UserAccountDeletionDto deletionDto) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        userAccountOpValidator.validateAccountDeletion(user, deletionDto);
        userRepository.delete(user);
    }
}
