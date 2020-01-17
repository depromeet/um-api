package com.depromeet.um.api.auth;

import com.depromeet.um.api.dto.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import org.jetbrains.annotations.NotNull;


@Getter
@ToString
public class UserSession extends AbstractAuthenticationToken {
    @NotNull
    private Long id;
    @NotNull
    private String umId;
    @NotNull
    private LoginType loginType;

    @Builder
    public UserSession(@NotNull Long id, @NotNull String umId, @NotNull LoginType loginType) {
        super(null);
        this.id = id;
        this.umId = umId;
        this.loginType = loginType;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
