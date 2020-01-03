package com.depromeet.team4.api.service;

import com.depromeet.team4.api.auth.TokenProvider;
import com.depromeet.team4.api.model.User;
import com.depromeet.team4.api.exception.AccountAlreadyExistException;
import com.depromeet.team4.api.dto.*;
import com.depromeet.team4.api.dto.LoginRequest;
import com.depromeet.team4.api.dto.RegisterRequest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AccountService {
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    protected AccountService(TokenProvider tokenProvider, UserService userService) {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }
    protected abstract void preRegister(RegisterRequest registerRequest);

    public Token register(RegisterRequest registerRequest) throws IllegalAccessException {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new AccountAlreadyExistException("Email duplicated");
        }
        this.preRegister(registerRequest);

        User user = userService.save(User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .loginType(registerRequest.getLoginType())
                .build());

        this.registerAccount(registerRequest, user);

        return tokenProvider.generatedToken(
                UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .loginType(user.getLoginType())
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }
    protected abstract void registerAccount(RegisterRequest registerRequest, User user);

    public Token login(LoginRequest loginRequest) throws IllegalAccessException {
        return tokenProvider.generatedToken(
                this.verifyAccount(loginRequest))
                .orElseThrow(IllegalArgumentException::new);
    }

    protected abstract UserDto verifyAccount(LoginRequest loginRequest);
}
