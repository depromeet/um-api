package com.depromeet.um.api.controller;

import com.depromeet.um.api.domain.model.ChatRoom;
import com.depromeet.um.api.dto.ChatRoomRequest;
import com.depromeet.um.api.dto.ChatRoomsResponse;
import com.depromeet.um.api.dto.MessageRequest;
import com.depromeet.um.api.domain.model.Message;
import com.depromeet.um.api.service.ChatMessageService;
import com.depromeet.um.api.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final ChatMessageService chatMessageService;

    public ChatController(ChatService chatService, ChatMessageService chatMessageService) {
        this.chatService = chatService;
        this.chatMessageService = chatMessageService;
    }

    @ApiOperation(value = "내 채팅 방정보들 API")
    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomsResponse> getChatRooms(){
        return ResponseEntity.ok(chatService.getChatRooms());
    }

    @ApiOperation(value = "채팅방 만들기")
    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest){
        return ResponseEntity.ok(chatService.createChatRoom(chatRoomRequest));
    }

    @ApiOperation(value = "채팅방 메시지 보내")
    @PostMapping("/rooms/{chatRoomId}/message")
    public ResponseEntity<Message> sendMessage(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody MessageRequest messageRequest) throws JsonProcessingException {
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, messageRequest));
    }

    @ApiOperation(value = "채팅방 메시지")
    @GetMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<Message> getMessages(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestBody MessageRequest messageRequest) throws JsonProcessingException {
        return ResponseEntity.ok(chatMessageService.sendMessage(chatRoomId, messageRequest));
    }
}
