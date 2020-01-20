package com.depromeet.um.api.service;

import com.depromeet.um.api.model.AppleUser;
import com.depromeet.um.api.repository.AppleUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppleUserService {
    private final AppleUserRepository appleUserRepository;

    public AppleUserService(AppleUserRepository appleUserRepository) {
        this.appleUserRepository = appleUserRepository;
    }

    public void save(AppleUser appleUser) {
        appleUserRepository.save(appleUser);
    }

    public AppleUser findByAppleId(String appleId) {
        return appleUserRepository.findById(appleId)
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean existsByAppleId(String appleId) {
        return appleUserRepository.existsById(appleId);
    }
}
