package com.depromeet.team4.api.service;

import com.depromeet.team4.api.dto.UserDto;
import com.depromeet.team4.api.model.User;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class UserSessionService {
    private final UserService userService;

    public UserSessionService(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        return ofNullable(UserThreadLocal.userThreadLocal.get())
                .orElse(userService.findById(this.getCurrentUserDto().getId()));
    }

    public UserDto getCurrentUserDto() {
        return UserDtoThreadLocal.userDtoThreadLocal.get();
    }

    public void setCurrentUserDto(UserDto userDto) {
        UserDtoThreadLocal.userDtoThreadLocal.set(userDto);
    }

    public void removeSession() {
        UserThreadLocal.userThreadLocal.remove();
        UserDtoThreadLocal.userDtoThreadLocal.remove();
    }
    private static class UserThreadLocal {
        private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    }

    private static class UserDtoThreadLocal {
        private static ThreadLocal<UserDto> userDtoThreadLocal = new ThreadLocal<>();
    }
}
