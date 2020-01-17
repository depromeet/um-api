package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    @NotNull
    private int status;
    @NotNull
    private String error;
    @NotNull
    private String message;
}
