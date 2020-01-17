package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.model.User;
import com.depromeet.um.api.exception.AccountAlreadyExistException;
import com.depromeet.um.api.dto.*;
import com.depromeet.um.api.dto.LoginRequest;
import com.depromeet.um.api.dto.RegisterRequest;
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
    protected abstract void beforeRegister(RegisterRequest registerRequest);

    public Token register(RegisterRequest registerRequest) throws IllegalArgumentException {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new AccountAlreadyExistException("Email duplicated");
        }
        this.beforeRegister(registerRequest);

        User user = userService.save(User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .loginType(registerRequest.getLoginType())
                .build());

        this.registerAccount(registerRequest, user);

        return tokenProvider.generatedToken(
                UserSession.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .loginType(user.getLoginType())
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }
    protected abstract void registerAccount(RegisterRequest registerRequest, User user);

    public Token login(LoginRequest loginRequest) throws IllegalArgumentException {
        return tokenProvider.generatedToken(this.verifyAccount(loginRequest))
                .orElseThrow(IllegalArgumentException::new);
    }

    protected abstract UserSession verifyAccount(LoginRequest loginRequest);
}
