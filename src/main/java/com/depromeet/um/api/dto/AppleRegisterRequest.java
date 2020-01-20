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
public class AppleRegisterRequest extends RegisterRequest {
    @NotNull
    private String appleId;
    @NotNull
    private String appleEmail;
    @NotNull
    private String accessToken;
}
