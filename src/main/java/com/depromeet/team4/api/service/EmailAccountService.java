package com.depromeet.team4.api.service;

import com.depromeet.team4.api.auth.TokenProvider;
import com.depromeet.team4.api.exception.AccountAlreadyExistException;
import com.depromeet.team4.api.model.EmailUser;
import com.depromeet.team4.api.dto.LoginType;
import com.depromeet.team4.api.model.User;
import com.depromeet.team4.api.dto.LoginRequest;
import com.depromeet.team4.api.dto.RegisterRequest;
import com.depromeet.team4.api.dto.UserDto;
import com.depromeet.team4.api.dto.EmailLoginRequest;
import com.depromeet.team4.api.dto.EmailRegisterRequest;
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
    protected UserDto verifyAccount(LoginRequest loginRequest) {
        EmailLoginRequest emailLoginRequest = (EmailLoginRequest) loginRequest;
        EmailUser emailUser = emailUserService.findByEmail(emailLoginRequest.getEmail());
        if (this.equalsPassword(emailLoginRequest.getPassword(), emailUser.getPassword())) {
            return UserDto.builder()
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
