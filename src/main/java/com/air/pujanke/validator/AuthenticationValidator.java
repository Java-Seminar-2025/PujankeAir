package com.air.pujanke.validator;

import com.air.pujanke.exception.exceptiontype.AttributeUniquenessException;
import com.air.pujanke.model.dto.UserRegistrationDto;
import com.air.pujanke.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationValidator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateRegistration(@NonNull UserRegistrationDto userDto) {
        if (!userDto.passwordPlain().equals(userDto.passwordConfirmPlain()))
            throw new RuntimeException("Passwords do not match.");
        else if (userRepository.existsByUsername(userDto.username()))
            throw new RuntimeException("Username already taken.");
    }
}
