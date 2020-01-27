package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.AppleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppleUserRepository extends JpaRepository<AppleUser, String> {
}
