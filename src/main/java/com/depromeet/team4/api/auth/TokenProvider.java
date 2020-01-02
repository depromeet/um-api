package com.depromeet.team4.api.auth;

import com.depromeet.team4.api.model.Token;
import com.depromeet.team4.api.model.UserDto;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public interface TokenProvider {
    Optional<Token> generatedToken(UserDto userDto);
    Optional<UserDto> verifyToken(String token, boolean isAccess);
}
