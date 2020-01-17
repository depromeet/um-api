package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.jetbrains.annotations.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class KakaoRegisterRequest extends RegisterRequest {
    @NotNull
    private String kakaoId;
    @NotNull
    private String kakaoEmail;
    @NotNull
    private String accessToken;
}
