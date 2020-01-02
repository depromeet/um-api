package com.depromeet.team4.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Builder
public abstract class RegisterRequest {
    protected RegisterRequest(){}
    private String email;
    private String password;
    private String username;
}
