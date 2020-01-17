package com.depromeet.um.api.model;

import com.depromeet.um.api.dto.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

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

    @Column
    @NotNull
    String umId;

    @Embedded
    UserName userName;

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull
    LoginType loginType;
}