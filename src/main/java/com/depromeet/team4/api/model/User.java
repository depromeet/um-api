package com.depromeet.team4.api.model;

import com.depromeet.team4.api.dto.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class User {
    protected User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    @NotNull
    String email;
    @Column
    @NotNull
    String username;
    @Enumerated(EnumType.STRING)
    @Column
    @NotNull
    LoginType loginType;
}