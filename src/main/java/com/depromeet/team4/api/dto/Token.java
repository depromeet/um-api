package com.depromeet.team4.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

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
