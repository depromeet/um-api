package com.depromeet.um.api.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenProvider tokenProvider;

    public TokenAuthenticationProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object token = authentication.getPrincipal();
        return tokenProvider.verifyToken((String) token, true)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with authentication token=" + token));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
