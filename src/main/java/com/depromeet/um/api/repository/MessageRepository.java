package com.depromeet.um.api.repository;

import com.depromeet.um.api.model.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    Iterable<Message> findByChatRoomId();
}
