package com.depromeet.um.api.auth;

import com.depromeet.um.api.dto.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.validation.constraints.NotNull;


@Getter
@ToString
public class UserSession extends AbstractAuthenticationToken {
    @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private LoginType loginType;

    @Builder
    public UserSession(Long id, String email, LoginType loginType) {
        super(null);
        this.id = id;
        this.email = email;
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
