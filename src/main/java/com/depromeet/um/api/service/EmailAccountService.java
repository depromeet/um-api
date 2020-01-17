package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.exception.AccountAlreadyExistException;
import com.depromeet.um.api.model.EmailUser;
import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.model.User;
import com.depromeet.um.api.dto.LoginRequest;
import com.depromeet.um.api.dto.RegisterRequest;
import com.depromeet.um.api.dto.EmailLoginRequest;
import com.depromeet.um.api.dto.EmailRegisterRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailAccountService extends AccountService {
    private final PasswordEncoder passwordEncoder;
    private final EmailUserService emailUserService;

    protected EmailAccountService(TokenProvider tokenProvider, UserService userService,
                                  EmailUserService emailUserService) {
        super(tokenProvider, userService);
        this.emailUserService = emailUserService;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void beforeRegister(RegisterRequest registerRequest) {
        registerRequest.setLoginType(LoginType.EMAIL);
    }

    @Override
    protected void registerAccount(RegisterRequest registerRequest, User user) {
        EmailRegisterRequest emailRegisterRequest = (EmailRegisterRequest) registerRequest;
        if (emailUserService.existsByEmail(emailRegisterRequest.getEmail())) {
            throw new AccountAlreadyExistException();
        }
        emailUserService.save(
                EmailUser.builder()
                        .email(emailRegisterRequest.getEmail())
                        .password(passwordEncoder.encode(emailRegisterRequest.getPassword()))
                        .user(user)
                        .build()
        );
    }

    @Override
    protected UserSession verifyAccount(LoginRequest loginRequest) {
        EmailLoginRequest emailLoginRequest = (EmailLoginRequest) loginRequest;
        EmailUser emailUser = emailUserService.findByEmail(emailLoginRequest.getEmail());
        if (this.equalsPassword(emailLoginRequest.getPassword(), emailUser.getPassword())) {
            return UserSession.builder()
                    .id(emailUser.getUser().getId())
                    .email(emailUser.getEmail())
                    .build();
        }
        throw new BadCredentialsException("Password not match");
    }

    public boolean equalsPassword(String requestPassword, String originPassword) {
        return passwordEncoder.matches(requestPassword, originPassword);
    }
}
