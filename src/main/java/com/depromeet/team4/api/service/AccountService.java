package com.depromeet.team4.api.service;

import com.depromeet.team4.api.auth.TokenProvider;
import com.depromeet.team4.api.model.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public abstract class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    protected AccountService(TokenProvider tokenProvider, UserService userService) {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    public Token register(RegisterRequest registerRequest) throws IllegalAccessException {
        userService.save(User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getPassword())
                .build());
        return login(LoginRequest.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build());
    }

    public Token login(LoginRequest loginRequest) throws IllegalAccessException {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (userService.equalsPassword(loginRequest.getPassword(), user.getPassword())) {
            return tokenProvider.generatedToken(UserDto.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .build())
                    .orElseThrow(IllegalArgumentException::new);
        }
        throw new IllegalAccessException("Login fail");
    }
}
