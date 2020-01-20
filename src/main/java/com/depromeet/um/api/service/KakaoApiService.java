package com.depromeet.um.api.service;

import com.depromeet.um.api.dto.KakaoTokenInfoResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoApiService {
    private static final String KAKAO_API_BASE_URL = "https://kapi.kakao.com";
    private static final String KAKAO_TOKEN_INFO_URL = KAKAO_API_BASE_URL + "/v1/user/access_token_info";
    private static final String KAKAO_USER_PROFILE_URL = KAKAO_API_BASE_URL + "/v2/user/me";

    private final WebClient webClient;

    public KakaoApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public KakaoTokenInfoResponse getKakaoTokenInfo(String accessToken) {
        return webClient.post()
                .uri(KAKAO_TOKEN_INFO_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoTokenInfoResponse.class)
                .block();
    }
}
