package com.depromeet.team4.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Builder
public abstract class LoginRequest {
    protected LoginRequest() {}

    private String email;
    private String password;
}
