package com.depromeet.um.api.auth;

import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.dto.Token;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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
    private static final String AUTHORITIES_UM_ID = "umId";
    private static final String AUTHORITIES_LOGIN_TYPE = "loginType";
    private static final String BEARER = "Bearer";
    @Override
    public Optional<Token> generatedToken(UserSession userSession) {
        return Optional.ofNullable(Token.builder()
                .accessToken(newToken(userSession, true))
                .refreshToken(newToken(userSession, false))
                .type(BEARER)
                .build());
    }

    private String newToken(UserSession userSession, boolean isAccessToken) {
        long now = (new Date()).getTime();
        Date validity = (isAccessToken) ? new Date(now + this.accessTokenExpirationSeconds*1000) :
                new Date(now + this.refreshTokenExpirationSeconds*1000);
        String key = (isAccessToken) ? this.accessSecretKey : this.refreshSecretKey;

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(AUTHORITIES_ID, userSession.getId())
                .claim(AUTHORITIES_UM_ID, userSession.getUmId())
                .claim(AUTHORITIES_LOGIN_TYPE, userSession.getLoginType())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.generateKey(key))
                .compact();

    }

    @Override
    public Optional<UserSession> verifyToken(String token, boolean isAccess) {
        String key = (isAccess) ? this.accessSecretKey : this.refreshSecretKey;
        String message = "";
        try {
            Jwts.parser().setSigningKey(this.generateKey(key)).parseClaimsJws(token);
            Claims claims = Jwts.parser().setSigningKey(this.generateKey(key))
                    .parseClaimsJws(token).getBody();
            return Optional.ofNullable(UserSession.builder()
                    .id(Long.valueOf((Integer) claims.get(AUTHORITIES_ID)))
                    .umId(claims.get(AUTHORITIES_UM_ID).toString())
                    .loginType(LoginType.valueOf(claims.get(AUTHORITIES_LOGIN_TYPE).toString()))
                    .build());
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature trace: {}", e);
            message = e.getMessage();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token trace: {}", e);
            message = e.getMessage();
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token trace: {}", e);
            message = e.getMessage();
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid trace: {}", e);
            message = e.getMessage();
        } catch (JwtException e) {
            log.error("JWT token are invalid trace: {}", e);
            message = e.getMessage();
        }
        throw new BadCredentialsException(message);
    }

    @Override
    public Optional<Token> refreshToken(String refreshToken) {
        UserSession userSession = this.verifyToken(refreshToken, false)
                .orElseThrow(()->new BadCredentialsException("Refresh Token is not verified"));
        return this.generatedToken(userSession);
    }

    private byte[] generateKey(String secretKey) {
        byte[] key = null;
        key = secretKey.getBytes(StandardCharsets.UTF_8);
        return key;
    }
}
