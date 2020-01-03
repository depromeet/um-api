package com.depromeet.team4.api.dto;

import com.depromeet.team4.api.dto.LoginType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public abstract class LoginRequest {
    @JsonIgnore
    LoginType loginType;
}
