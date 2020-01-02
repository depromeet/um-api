package com.depromeet.team4.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Token {
    protected Token(){}
    private String accessToken;
    private String refreshToken;
    private String type;
}
