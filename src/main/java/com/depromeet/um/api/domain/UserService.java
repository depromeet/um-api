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

    public User save(User user) {
        return userRepository.save(user);
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
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<User> findAllByUserIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public long countByUserIds(List<Long> userIds) {
        return userRepository.countByIds(userIds);
    }

    public User findByUmId(String umId) {
        return userRepository.findByUmId(umId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByUmId(String umId) {
        return userRepository.existsByUmId(umId);
    }
}
