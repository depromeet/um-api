package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.domain.AppleUserService;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.dto.AppleLoginRequest;
import com.depromeet.um.api.dto.AppleRegisterRequest;
import com.depromeet.um.api.dto.LoginRequest;
import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.dto.RegisterRequest;
import com.depromeet.um.api.domain.model.AppleUser;
import com.depromeet.um.api.domain.model.User;
import com.depromeet.um.api.util.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AppleAccountService extends AccountService {
    private final AppleUserService appleUserService;
    private final AppleVerifyService appleVerifyService;
    private final UserService userService;
    private static final String APPLE_PREFIX = StringUtils.UM_PREFIX + "APPLE";

    protected AppleAccountService(TokenProvider tokenProvider, UserService userService, AppleUserService appleUserService, AppleVerifyService appleVerifyService) {
        super(tokenProvider, userService);
        this.appleUserService = appleUserService;
        this.userService = userService;
        this.appleVerifyService = appleVerifyService;
    }

    @Override
    protected boolean isExistAccount(RegisterRequest registerRequest) {
        return appleUserService.existsByAppleId(((AppleRegisterRequest) registerRequest).getAppleId());
    }

    @Override
    protected User registerAccount(RegisterRequest registerRequest) {
        AppleRegisterRequest appleRegisterRequest = (AppleRegisterRequest) registerRequest;
        User user = userService.saveAndGet(User.builder()
                .umId(appleRegisterRequest.getAppleId() + APPLE_PREFIX)
                .loginType(LoginType.APPLE)
                .build());

        AppleUser appleUser = AppleUser.builder()
                .appleId(appleRegisterRequest.getAppleId())
                .appleEmail(appleRegisterRequest.getAppleEmail())
                .lastAccessToken(appleRegisterRequest.getAccessToken())
                .user(user)
                .build();

        if (appleVerifyService.verifyAccessToken(appleUser, appleRegisterRequest.getAccessToken())) {
            appleUserService.save(appleUser);
        }
        return user;
    }

    @Override
    protected UserSession verifyAccount(LoginRequest loginRequest) {
        AppleLoginRequest appleLoginRequest = (AppleLoginRequest) loginRequest;
        AppleUser appleUser = appleUserService.findByAppleId(appleLoginRequest.getAppleId());
        if (appleVerifyService.verifyAccessToken(appleUser, appleLoginRequest.getAccessToken())) {
            return UserSession.builder()
                    .id(appleUser.getUser().getId())
                    .umId(appleUser.getUser().getUmId())
                    .loginType(LoginType.APPLE)
                    .build();
        }
        throw new BadCredentialsException("Apple token invalid");
    }
}
