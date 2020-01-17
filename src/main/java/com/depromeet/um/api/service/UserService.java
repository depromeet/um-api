package com.depromeet.um.api.service;

import com.depromeet.um.api.model.User;
import com.depromeet.um.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public User findById(Long id) {
        log.info("find by id :{}",id);
        return userRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);
    }

    public User findByUmId(String umId) {
        return userRepository.findByUmId(umId)
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean existsByUmId(String umId) {
        return userRepository.existsByUmId(umId);
    }
}
