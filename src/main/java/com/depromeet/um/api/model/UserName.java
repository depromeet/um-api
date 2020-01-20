package com.depromeet.um.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserName {
    protected UserName() {
    }

    @NotNull
    @Setter
    String firstName;

    @NotNull
    @Setter
    String lastName;

    @NotNull
    @Setter
    String nickName;
}
