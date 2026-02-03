package com.air.pujanke.service;

import com.air.pujanke.model.dto.UserRegistrationDto;
import com.air.pujanke.model.entity.UserEntity;
import com.air.pujanke.repository.UserRepository;
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
        var mapper = new ObjectMapper();
        // Because of passwordPlain, jackson would fail by default because DTO is different than Entity.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var user = mapper.convertValue(userDto, UserEntity.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.passwordPlain()));
        user.setIsEnabled(true);
        userRepository.save(user);
    }
}
