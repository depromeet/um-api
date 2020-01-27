package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.User;
import com.depromeet.um.api.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User saveAndGet(User user) {
        userRepository.save(user);
        return user;
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserId(Long userId) {
        log.info("Find user by id :{}",userId);
        return userRepository.findById(userId)
                .orElseThrow(IllegalAccessError::new);
    }

    public List<User> findAllByUserIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public User findByUmId(String umId) {
        return userRepository.findByUmId(umId)
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean existsByUmId(String umId) {
        return userRepository.existsByUmId(umId);
    }
}
