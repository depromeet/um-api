package com.depromeet.um.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public abstract class RegisterRequest {
    @NotNull
    private String email;
    @NotNull
    private String username;
    @JsonIgnore
    @NotNull
    private LoginType loginType;

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
