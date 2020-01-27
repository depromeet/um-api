package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoUserRepository extends JpaRepository<KakaoUser, String> {
}
