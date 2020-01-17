package com.depromeet.um.api.repository;

import com.depromeet.um.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUmId(String umId);
    boolean existsByUmId(String umId);
}

