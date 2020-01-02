package com.depromeet.team4.api.service;

import com.depromeet.team4.api.auth.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class EmailAccountService extends AccountService{
    protected EmailAccountService(TokenProvider tokenProvider, UserService userService) {
        super(tokenProvider, userService);
    }
}
