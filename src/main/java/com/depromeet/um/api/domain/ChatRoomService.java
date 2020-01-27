package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.ChatRoom;
import com.depromeet.um.api.domain.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MongoSequenceGeneratorService mongoSequenceGeneratorService;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, MongoSequenceGeneratorService mongoSequenceGeneratorService) {
        this.chatRoomRepository = chatRoomRepository;
        this.mongoSequenceGeneratorService = mongoSequenceGeneratorService;
    }

    public Long generateSequenceId() {
        return mongoSequenceGeneratorService.generateSequence(ChatRoom.SEQUENCE_NAME);
    }
    public List<ChatRoom> findAllByChatRoomIds(List<Long> chatRoomIds) {
        return (List<ChatRoom>) chatRoomRepository.findAllById(chatRoomIds);
    }
    public ChatRoom findByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(IllegalAccessError::new);
    }
    public ChatRoom test(Long chatRoomId, Long userId) {
        System.out.println(chatRoomRepository.existsByIdAndJoinUserIdsIn(chatRoomId,3L));
        return chatRoomRepository.findByIdAndAndJoinUserIdsIn(chatRoomId,userId);
    }

    public void save(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }
}
