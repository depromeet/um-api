package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.domain.EmailUserService;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.domain.model.EmailUser;
import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.domain.model.User;
import com.depromeet.um.api.dto.LoginRequest;
import com.depromeet.um.api.dto.RegisterRequest;
import com.depromeet.um.api.dto.EmailLoginRequest;
import com.depromeet.um.api.dto.EmailRegisterRequest;
import com.depromeet.um.api.util.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailAccountService extends AccountService {
    private final PasswordEncoder passwordEncoder;
    private final EmailUserService emailUserService;
    private final UserService userService;
    private static final String EMAIL_PREFIX = StringUtils.UM_PREFIX + "EMAIL";

    protected EmailAccountService(TokenProvider tokenProvider, UserService userService,
                                  EmailUserService emailUserService, UserService userService1) {
        super(tokenProvider, userService);
        this.emailUserService = emailUserService;
        this.userService = userService1;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected boolean isExistAccount(RegisterRequest registerRequest) {
        return emailUserService.existsByEmail(((EmailRegisterRequest) registerRequest).getEmail());
    }

    @Override
    protected User registerAccount(RegisterRequest registerRequest) {
        EmailRegisterRequest emailRegisterRequest = (EmailRegisterRequest) registerRequest;
        User user = userService.save(User.builder()
                .umId(emailRegisterRequest.getEmail() + EMAIL_PREFIX)
                .loginType(LoginType.EMAIL)
                .build());

        emailUserService.save(
                EmailUser.builder()
                        .email(emailRegisterRequest.getEmail())
                        .password(passwordEncoder.encode(emailRegisterRequest.getPassword()))
                        .user(user)
                        .build()
        );
        return user;
    }

    @Override
    protected UserSession verifyAccount(LoginRequest loginRequest) {
        EmailLoginRequest emailLoginRequest = (EmailLoginRequest) loginRequest;
        EmailUser emailUser = emailUserService.findByEmail(emailLoginRequest.getEmail());
        if (this.equalsPassword(emailLoginRequest.getPassword(), emailUser.getPassword())) {
            return UserSession.builder()
                    .id(emailUser.getUser().getId())
                    .umId(emailUser.getUser().getUmId())
                    .loginType(LoginType.EMAIL)
                    .build();
        }
        throw new BadCredentialsException("Password not match");
    }

    public boolean equalsPassword(String requestPassword, String originPassword) {
        return passwordEncoder.matches(requestPassword, originPassword);
    }
}
