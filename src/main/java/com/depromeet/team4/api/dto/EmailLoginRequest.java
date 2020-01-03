package com.depromeet.team4.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class EmailLoginRequest extends LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}