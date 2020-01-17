package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class Token {
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
    @NotNull
    private String type;
}
