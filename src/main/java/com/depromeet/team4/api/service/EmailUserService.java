package com.depromeet.team4.api.service;

import com.depromeet.team4.api.model.EmailUser;
import com.depromeet.team4.api.repository.EmailUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailUserService {
    private final EmailUserRepository emailUserRepository;

    public EmailUserService(EmailUserRepository emailUserRepository) {
        this.emailUserRepository = emailUserRepository;
    }

    public void save(EmailUser emailUser) {
        emailUserRepository.save(emailUser);
    }

    public EmailUser findByEmail(String email) {
        return emailUserRepository.findById(email)
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean existsByEmail(String email) {
        return emailUserRepository.existsById(email);
    }
}
