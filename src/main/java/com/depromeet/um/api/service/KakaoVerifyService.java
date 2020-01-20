package com.depromeet.um.api.service;

import com.depromeet.um.api.dto.KakaoTokenInfoResponse;
import com.depromeet.um.api.model.KakaoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KakaoVerifyService {
    private final KakaoApiService kakaoApiService;

    public KakaoVerifyService(KakaoApiService kakaoApiService) {
        this.kakaoApiService = kakaoApiService;
    }

    public boolean verifyAccessToken(KakaoUser kakaoUser, String accessToken) {
        if (kakaoUser.getLastAccessToken().equals(accessToken)) {
            log.info("Last accessToken 과 동일합니다.");
            return true;
        }

        KakaoTokenInfoResponse kakaoTokenInfoResponse = kakaoApiService.getKakaoTokenInfo(accessToken);
        String accessKakaoId = kakaoTokenInfoResponse.getId().toString();
        if (accessKakaoId.equals(kakaoUser.getKakaoId())) {
            kakaoUser.setLastAccessToken(accessToken);
            return true;
        }

        throw new BadCredentialsException("Request kakao token is invalid");
    }
}
