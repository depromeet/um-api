package com.depromeet.um.api.domain.repository;

import com.depromeet.um.api.domain.model.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    Iterable<Message> findByChatRoomId();
}
