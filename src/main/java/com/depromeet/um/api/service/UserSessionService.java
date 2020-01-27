package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.domain.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class UserSessionService {
    private final UserService userService;
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public UserSessionService(UserService userService) {
        this.userService = userService;
    }


    public User getCurrentUser() {
        return ofNullable(userThreadLocal.get()).orElseGet(this::loadUser);
    }

    private User loadUser() {
        User user = userService.findByUserId(this.getCurrentUserSession().getId());
        userThreadLocal.set(user);
        return user;
    }
    public UserSession getCurrentUserSession() {
        return (UserSession) SecurityContextHolder.getContext().getAuthentication();
    }

    public void removeSession() {
        userThreadLocal.remove();
        SecurityContextHolder.clearContext();
    }
}
