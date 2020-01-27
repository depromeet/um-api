package com.depromeet.um.api.service;

import com.depromeet.um.api.auth.UserSession;
import com.depromeet.um.api.config.MQTTConfig;
import com.depromeet.um.api.domain.ChatRoomService;
import com.depromeet.um.api.domain.MessageService;
import com.depromeet.um.api.dto.MessageRequest;
import com.depromeet.um.api.domain.model.ChatRoom;
import com.depromeet.um.api.domain.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class ChatMessageService {
    private final MQTTConfig.MQTTGateway mqttGateway;
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final UserSessionService userSessionService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ChatMessageService(MQTTConfig.MQTTGateway mqttGateway, MessageService messageService,
                              ChatRoomService chatRoomService, UserSessionService userSessionService) {
        this.mqttGateway = mqttGateway;
        this.messageService = messageService;
        this.chatRoomService = chatRoomService;
        this.userSessionService = userSessionService;
    }

    public Message sendMessage(Long chatRoomId, MessageRequest messageRequest) throws JsonProcessingException {
        ChatRoom chatRoom = chatRoomService.findByChatRoomId(chatRoomId);

        UserSession userSession = userSessionService.getCurrentUserSession();
        String brokerChannel = chatRoom.getBrokerChannel();
        String content = messageRequest.getContent();
        LocalDateTime createTime = LocalDateTime.now();

        Message message = Message.builder()
                .authorId(userSession.getId())
                .brokerChannel(brokerChannel)
                .chatRoomId(chatRoomId)
                .content(content)
                .createTime(createTime)
                .build();

        messageService.save(message);

        mqttGateway.publish(brokerChannel, objectMapper.writeValueAsString(message));

        return message;
    }
}
