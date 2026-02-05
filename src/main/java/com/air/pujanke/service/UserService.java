package com.air.pujanke.service;

import com.air.pujanke.exception.exceptiontype.UserNotFoundException;
import com.air.pujanke.model.dto.UserAccountPageDetailsDto;
import com.air.pujanke.model.dto.UserRegistrationDto;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.UserRepository;
import com.air.pujanke.utility.ConfiguredObjectMapper;
import com.air.pujanke.validator.AuthenticationValidator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationValidator authValidator;

    public boolean isUsernameTaken(@NonNull String username) {
        return userRepository.existsByUsername(username);
    }

    public void createUser(UserRegistrationDto userDto) {
        authValidator.validateRegistration(userDto);
        var user = ConfiguredObjectMapper.getMapper().convertValue(userDto, UserEntity.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.passwordPlain()));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public UserAccountPageDetailsDto fetchUserAccountPageDetails(@NonNull String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
        return ConfiguredObjectMapper.getMapper().convertValue(user, UserAccountPageDetailsDto.class);
    }
}
