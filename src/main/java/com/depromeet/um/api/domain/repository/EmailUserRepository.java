package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailUserRepository extends JpaRepository<EmailUser, String> {
}
