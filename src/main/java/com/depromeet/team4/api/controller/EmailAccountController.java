package com.depromeet.team4.api.controller;

import com.depromeet.team4.api.dto.Token;
import com.depromeet.team4.api.dto.EmailLoginRequest;
import com.depromeet.team4.api.dto.EmailRegisterRequest;
import com.depromeet.team4.api.service.EmailAccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/email")
public class EmailAccountController {

    private final EmailAccountService emailAccountService;

    public EmailAccountController(EmailAccountService emailAccountService) {
        this.emailAccountService = emailAccountService;
    }

    @ApiOperation(value = "Email 회원가입 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/register")
    public Token register(@RequestBody EmailRegisterRequest emailRegisterRequest) throws IllegalAccessException {
        return emailAccountService.register(emailRegisterRequest);
    }

    @ApiOperation(value = "Email 로그인 API", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @Transactional
    @PostMapping("/login")
    public Token register(@RequestBody EmailLoginRequest emailLoginRequest) throws IllegalAccessException {
        return emailAccountService.login(emailLoginRequest);
    }
}
