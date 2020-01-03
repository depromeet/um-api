package com.depromeet.team4.api.repository;

import com.depromeet.team4.api.model.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailUserRepository extends JpaRepository<EmailUser, String> {
}
