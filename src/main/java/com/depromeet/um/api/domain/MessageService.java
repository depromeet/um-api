package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.Message;
import com.depromeet.um.api.domain.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

}
