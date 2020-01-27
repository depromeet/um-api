package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.Message;
import com.depromeet.um.api.domain.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MongoSequenceGeneratorService mongoSequenceGeneratorService;

    public MessageService(MessageRepository messageRepository, MongoSequenceGeneratorService mongoSequenceGeneratorService) {
        this.messageRepository = messageRepository;
        this.mongoSequenceGeneratorService = mongoSequenceGeneratorService;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Long generateSequenceNo(Long chatRoomId) {
        return mongoSequenceGeneratorService.generateSequence(Message.SEQUENCE_NAME + "#" +chatRoomId);
    }
}
