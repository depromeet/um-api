package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, Long> {
    ChatRoom findByIdAndAndJoinUserIdsIn(Long id, Long userId);
    boolean existsByIdAndJoinUserIdsIn(Long id, Long userId);
}
