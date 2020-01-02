package com.depromeet.team4.api.auth;


import com.depromeet.team4.api.model.UserDto;
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
        log.info("authenticate : {}", authentication);
        Object token = authentication.getPrincipal();
        UserDto userDto = tokenProvider.verifyToken((String) token, true)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with authentication token=" + token));
        log.info("verify user : {}",userDto);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
