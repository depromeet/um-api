package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.UserChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRoomRepository extends MongoRepository<UserChatRoom, Long> {
    boolean existsByUserIdAndDirectChatRoomIdsIn(Long id, Long chatRoomId);
    boolean existsByDirectChatRoomIds(List<Long> chatRoomIds);
}
