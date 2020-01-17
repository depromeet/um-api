package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.jetbrains.annotations.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    @NotNull
    private String error;
    @NotNull
    private String message;
}
