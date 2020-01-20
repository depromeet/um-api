package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class KakaoTokenInfoResponse {
    @NotNull
    private Long id;
    @NotNull
    private Long expiresInMillis;
    @NotNull
    private Long appId;
}
