package com.depromeet.um.api.auth;

import com.depromeet.um.api.dto.Token;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TokenProvider {
    Optional<Token> generatedToken(UserSession userSession);
    Optional<UserSession> verifyToken(String token, boolean isAccess);
    Optional<Token> refreshToken(String refreshToken);
}
