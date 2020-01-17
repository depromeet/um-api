package com.depromeet.um.api.controller;

import com.depromeet.um.api.auth.TokenProvider;
import com.depromeet.um.api.dto.KakaoLoginRequest;
import com.depromeet.um.api.dto.KakaoRegisterRequest;
import com.depromeet.um.api.dto.Token;
import com.depromeet.um.api.dto.EmailLoginRequest;
import com.depromeet.um.api.dto.EmailRegisterRequest;
import com.depromeet.um.api.service.EmailAccountService;
import com.depromeet.um.api.service.KakaoAccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    private final EmailAccountService emailAccountService;
    private final KakaoAccountService kakaoAccountService;
    private final TokenProvider tokenProvider;

    public AccountController(EmailAccountService emailAccountService, KakaoAccountService kakaoAccountService, TokenProvider tokenProvider) {
        this.emailAccountService = emailAccountService;
        this.kakaoAccountService = kakaoAccountService;
        this.tokenProvider = tokenProvider;
    }

    @ApiOperation(value = "Update Access Token API", notes = "refresh_token 을 보내면 access_token 을 반환해주는 API. (Authorization Header 필요 없습니다. Swagger 전역 설정 원인)")
    @GetMapping("/refresh/{token:.+}")
    public ResponseEntity<Token> refreshToken(@PathVariable("token") String refreshToken) {
        return ResponseEntity.ok(tokenProvider.refreshToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("Refresh Token is not verified")));
    }

    @ApiOperation(value = "Update Access Token API", notes = "access_token 을 보내면 토큰 상태를 반환해주는 API. (Authorization Header 필요 없습니다. Swagger 전역 설정 원인)")
    @GetMapping("/verify/{token:.+}")
    public ResponseEntity verifyToken(@PathVariable("token") String accessToken) {
        tokenProvider.verifyToken(accessToken, true);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(value = "Email 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register/email")
    public ResponseEntity<Token> registerWithEmail(@RequestBody EmailRegisterRequest emailRegisterRequest) throws IllegalArgumentException {
        return ResponseEntity.ok(emailAccountService.register(emailRegisterRequest));
    }

    @ApiOperation(value = "Email 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login/email")
    public ResponseEntity<Token> loginWithEmail(@RequestBody EmailLoginRequest emailLoginRequest) throws IllegalArgumentException {
        return ResponseEntity.ok(emailAccountService.login(emailLoginRequest));
    }

    @ApiOperation(value = "Kakao 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register/kakao")
    public ResponseEntity<Token> registerWithKakao(@RequestBody KakaoRegisterRequest kakaoRegisterRequest) throws IllegalArgumentException {
        return ResponseEntity.ok(kakaoAccountService.register(kakaoRegisterRequest));
    }

    @ApiOperation(value = "Kakao 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login/kakao")
    public ResponseEntity<Token> loginWithKakao(@RequestBody KakaoLoginRequest kakaoLoginRequest) throws IllegalArgumentException {
        return ResponseEntity.ok(kakaoAccountService.login(kakaoLoginRequest));
    }
}
