package com.depromeet.team4.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class UserDto {
    protected UserDto(){}
    @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private LoginType loginType;
}
