package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.EmailUser;
import com.depromeet.um.api.domain.repository.EmailUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailUserService {
    private final EmailUserRepository emailUserRepository;

    public EmailUserService(EmailUserRepository emailUserRepository) {
        this.emailUserRepository = emailUserRepository;
    }

    public EmailUser save(EmailUser emailUser) {
        return emailUserRepository.save(emailUser);
    }

    public EmailUser findByEmail(String email) {
        return emailUserRepository.findById(email)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByEmail(String email) {
        return emailUserRepository.existsById(email);
    }
}
