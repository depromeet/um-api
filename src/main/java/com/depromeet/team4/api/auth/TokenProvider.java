package com.depromeet.team4.api.auth;

import com.depromeet.team4.api.dto.Token;
import com.depromeet.team4.api.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TokenProvider {
    Optional<Token> generatedToken(UserDto userDto);
    Optional<UserDto> verifyToken(String token, boolean isAccess);
}
