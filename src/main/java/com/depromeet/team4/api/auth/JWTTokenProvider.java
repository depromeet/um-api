package com.depromeet.team4.api.auth;

import com.depromeet.team4.api.model.Token;
import com.depromeet.team4.api.model.UserDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JWTTokenProvider implements TokenProvider {
    @Value("${jwt.secret.base64.access}")
    private String accessSecretKey;
    @Value("${jwt.secret.base64.refresh}")
    private String refreshSecretKey;
    @Value("${jwt.expiration-seconds.access}")
    private int accessTokenExpirationSeconds;
    @Value("${jwt.expiration-seconds.refresh}")
    private int refreshTokenExpirationSeconds;

    private static final String AUTHORITIES_ID = "userId";
    private static final String AUTHORITIES_Email = "userEmail";
    private static final String BEARER = "Bearer";
    @Override
    public Optional<Token> generatedToken(UserDto userDto) {
        Long userId = userDto.getId();
        String userEmail = userDto.getEmail();
        return Optional.ofNullable(Token.builder()
                .accessToken(newToken(userId, userEmail, true))
                .refreshToken(newToken(userId, userEmail, false))
                .type(BEARER)
                .build());
    }

    private String newToken(Long userId, String userEmail, boolean isAccessToken) {
        long now = (new Date()).getTime();
        Date validity = (isAccessToken) ? new Date(now + this.accessTokenExpirationSeconds*1000) :
                new Date(now + this.refreshTokenExpirationSeconds*1000);
        String key = (isAccessToken) ? this.accessSecretKey : this.refreshSecretKey;

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(AUTHORITIES_ID,userId)
                .claim(AUTHORITIES_Email, userEmail)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.generateKey(key))
                .compact();

    }

    @Override
    public Optional<UserDto> verifyToken(String token, boolean isAccess) {
        String key = (isAccess) ? this.accessSecretKey : this.refreshSecretKey;
        String message = "";
        try {
            Jwts.parser().setSigningKey(this.generateKey(key)).parseClaimsJws(token);
            Claims claims = Jwts.parser().setSigningKey(this.generateKey(key))
                    .parseClaimsJws(token).getBody();
            return Optional.ofNullable(UserDto.builder()
                    .id(Long.valueOf((Integer) claims.get(AUTHORITIES_ID)))
                    .email(claims.get(AUTHORITIES_Email).toString())
                    .build());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
            message = e.getMessage();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
            message = e.getMessage();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
            message = e.getMessage();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
            message = e.getMessage();
        } catch (JwtException e) {
            log.info("JWT token are invalid.");
            log.trace("JWT token are invalid trace: {}", e);
            message = e.getMessage();
        }
        throw new RuntimeException(message);
    }

    private byte[] generateKey(String secretKey) {
        byte[] key = null;
        try {
            key = secretKey.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }
}
