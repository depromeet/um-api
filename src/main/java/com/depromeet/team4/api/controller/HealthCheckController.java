package com.depromeet.team4.api.controller;

import com.depromeet.team4.api.dto.UserDto;
import com.depromeet.team4.api.service.UserSessionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HealthCheckController {
    private final UserSessionService userSessionService;

    public HealthCheckController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @ApiOperation(value = "Health Checker", notes = "Authorization Header 필요 없습니다. Swagger 전역 설정 원인")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "Health Checker With Token", notes = "Token 을 가지고 health check")
    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheckApi() {
        UserDto userDto = userSessionService.getCurrentUserDto();
        return ResponseEntity.ok("OK");
    }
}
