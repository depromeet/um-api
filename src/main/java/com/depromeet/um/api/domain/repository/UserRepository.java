package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUmId(String umId);
    boolean existsByUmId(String umId);
    @Query("SELECT count(*) FROM User u where u.id in :ids")
    long countByIds(@Param("ids") List<Long> ids);
}

