package com.depromeet.team4.api.controller;

import com.depromeet.team4.api.dto.UserDto;
import com.depromeet.team4.api.service.UserSessionService;
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

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheckApi() {
        UserDto userDto = userSessionService.getCurrentUserDto();
        log.info("Auth check {}", userDto.toString());
        return ResponseEntity.ok("OK");
    }
}
