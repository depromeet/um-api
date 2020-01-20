package com.depromeet.um.api.model;

import com.depromeet.um.api.dto.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
public class User {
    protected User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String umId;

    @Embedded
    UserName userName;

    @Nullable
    String publicEmail;

    @Enumerated(EnumType.STRING)
    @NotNull
    LoginType loginType;
}