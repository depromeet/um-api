package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.AppleUser;
import com.depromeet.um.api.domain.repository.AppleUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppleUserService {
    private final AppleUserRepository appleUserRepository;

    public AppleUserService(AppleUserRepository appleUserRepository) {
        this.appleUserRepository = appleUserRepository;
    }

    public AppleUser save(AppleUser appleUser) {
        return appleUserRepository.save(appleUser);
    }

    public AppleUser findByAppleId(String appleId) {
        return appleUserRepository.findById(appleId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByAppleId(String appleId) {
        return appleUserRepository.existsById(appleId);
    }
}
