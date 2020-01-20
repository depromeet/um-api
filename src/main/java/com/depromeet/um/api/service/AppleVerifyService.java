package com.depromeet.um.api.service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.depromeet.um.api.model.AppleUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;

@Service
public class AppleVerifyService {
    private static final String APPLE_TOKEN_ISSUER = "https://appleid.apple.com";
    private static final String APPLE_PUBLIC_KEYS_URL = "https://appleid.apple.com/auth/keys";
    private static final JwkProvider APPLE_PUBLIC_JWK_PROVIDER = new UrlJwkProvider(APPLE_PUBLIC_KEYS_URL);

    // TODO 개발자 등록 완료시 작성
    @Value("${apple.clientId:umtemp}")
    private String clientId;

    public boolean verifyAccessToken(AppleUser appleUser, String accessToken) {
        if (appleUser.getLastAccessToken().equals(accessToken)) {
            return true;
        }

        try {
            DecodedJWT decodedJWT = JWT.decode(accessToken);
            Jwk jwk = APPLE_PUBLIC_JWK_PROVIDER.get(decodedJWT.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(APPLE_TOKEN_ISSUER)
                    //.withAudience(clientId)
                    .build();
            verifier.verify(decodedJWT);
        } catch (JwkException | TokenExpiredException | JWTDecodeException | InvalidClaimException e) {
            throw new BadCredentialsException("Request apple token invalid");
        }
        appleUser.setLastAccessToken(accessToken);
        return true;
    }
}
