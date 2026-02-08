package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.InvalidArgumentException;
import com.air.pujanke.model.dto.*;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.UserRepository;
import com.air.pujanke.service.validator.UserAccountOperationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountOperationValidator userAccountOpValidator;
    private final ObjectMapper mapper;

    public boolean isUsernameTaken(@NonNull String username) {
        return userRepository.existsByUsername(username);
    }

    public void createUser(UserRegistrationDto userDto) {
        userAccountOpValidator.validateRegistration(userDto);
        var user = mapper.convertValue(userDto, UserEntity.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.passwordPlain()));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public UserAccountPageDetailsDto fetchUserAccountPageDetails(@NonNull String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        return mapper.convertValue(user, UserAccountPageDetailsDto.class);
    }

    public List<UserManagementDto> fetchUsersForManagement() {
        List<UserManagementDto> userDtos = new ArrayList<>();
        userRepository.findAllByHasAdminPrivilegesFalse().forEach(user -> userDtos.add(mapper.convertValue(user, UserManagementDto.class)));
        return userDtos;
    }


    @Transactional
    public void modifyUserFunds(@NonNull String username, UserFundModificationDto fundDto) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
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
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        userAccountOpValidator.validatePasswordReset(user, passwordResetDto);
        user.setPasswordHash(passwordEncoder.encode(passwordResetDto.newPassword()));
    }

    @Transactional
    public void deleteAccount(@NonNull String username, UserAccountDeletionDto deletionDto) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        userAccountOpValidator.validateAccountDeletion(user, deletionDto);
        userRepository.delete(user);
    }
    
    @Transactional
    public void adminDeleteAccount(@NonNull String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        userRepository.delete(user);
    }

    @Transactional
    public void toggleAccountEnabled(@NonNull String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new InvalidArgumentException("User not found."));
        user.setIsEnabled(!user.getIsEnabled());
        userRepository.save(user);
    }
}
