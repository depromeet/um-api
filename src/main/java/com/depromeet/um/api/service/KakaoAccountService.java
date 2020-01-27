package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.domain.KakaoUserService;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.dto.KakaoLoginRequest;
import com.depromeet.um.api.dto.KakaoRegisterRequest;
import com.depromeet.um.api.dto.LoginRequest;
import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.dto.RegisterRequest;
import com.depromeet.um.api.domain.model.KakaoUser;
import com.depromeet.um.api.domain.model.User;
import com.depromeet.um.api.util.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class KakaoAccountService extends AccountService {
    private final KakaoUserService kakaoUserService;
    private final KakaoVerifyService kakaoVerifyService;
    private final UserService userService;
    private static final String KAKAO_PREFIX = StringUtils.UM_PREFIX + "KAKAO";

    protected KakaoAccountService(TokenProvider tokenProvider, UserService userService, KakaoUserService kakaoUserService, KakaoVerifyService kakaoVerifyService, UserService userService1) {
        super(tokenProvider, userService);
        this.kakaoUserService = kakaoUserService;
        this.kakaoVerifyService = kakaoVerifyService;
        this.userService = userService1;
    }

    @Override
    protected boolean isExistAccount(RegisterRequest registerRequest) {
        return kakaoUserService.existsByKakaoId(((KakaoRegisterRequest) registerRequest).getKakaoId());
    }

    @Override
    protected User registerAccount(RegisterRequest registerRequest) {
        KakaoRegisterRequest kakaoRegisterRequest = (KakaoRegisterRequest) registerRequest;
        User user = userService.saveAndGet(User.builder()
                .umId(kakaoRegisterRequest.getKakaoId() + KAKAO_PREFIX)
                .loginType(LoginType.KAKAO)
                .build());

        KakaoUser kakaoUser = KakaoUser.builder()
                .kakaoId(kakaoRegisterRequest.getKakaoId())
                .kakaoEmail(kakaoRegisterRequest.getKakaoEmail())
                .lastAccessToken(kakaoRegisterRequest.getAccessToken())
                .user(user)
                .build();

        if (kakaoVerifyService.verifyAccessToken(kakaoUser, kakaoRegisterRequest.getAccessToken())) {
            kakaoUserService.save(kakaoUser);
        }

        return user;
    }

    @Override
    protected UserSession verifyAccount(LoginRequest loginRequest) {
        KakaoLoginRequest kakaoLoginRequest = (KakaoLoginRequest) loginRequest;
        KakaoUser kakaoUser = kakaoUserService.findByKakaoId(kakaoLoginRequest.getKakaoId());
        if (kakaoVerifyService.verifyAccessToken(kakaoUser, kakaoLoginRequest.getAccessToken())) {
            return UserSession.builder()
                    .id(kakaoUser.getUser().getId())
                    .umId(kakaoUser.getUser().getUmId())
                    .loginType(LoginType.KAKAO)
                    .build();
        }
        throw new BadCredentialsException("Kakao token invalid");
    }
}
