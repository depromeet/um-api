package com.depromeet.team4.api.auth;


import com.depromeet.team4.api.dto.UserDto;
import com.depromeet.team4.api.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenProvider tokenProvider;
    private final UserSessionService userSessionService;

    public TokenAuthenticationProvider(TokenProvider tokenProvider, UserSessionService userSessionService) {
        this.tokenProvider = tokenProvider;
        this.userSessionService = userSessionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object token = authentication.getPrincipal();
        UserDto userDto = tokenProvider.verifyToken((String) token, true)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find user with authentication token=" + token));
        userSessionService.setCurrentUserDto(userDto);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
