package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.domain.model.User;
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

    protected abstract boolean isExistAccount(RegisterRequest registerRequest);

    public Token register(RegisterRequest registerRequest) throws IllegalArgumentException {
        if (this.isExistAccount(registerRequest)) {
            throw new AccountAlreadyExistException("Account Duplicated");
        }
        User user = this.registerAccount(registerRequest);

        return tokenProvider.generatedToken(
                UserSession.builder()
                        .id(user.getId())
                        .umId(user.getUmId())
                        .loginType(user.getLoginType())
                        .build())
                .orElseThrow(IllegalArgumentException::new);
    }

    protected abstract User registerAccount(RegisterRequest registerRequest);

    public Token login(LoginRequest loginRequest) throws IllegalArgumentException {
        return tokenProvider.generatedToken(this.verifyAccount(loginRequest))
                .orElseThrow(IllegalArgumentException::new);
    }

    protected abstract UserSession verifyAccount(LoginRequest loginRequest);
}
