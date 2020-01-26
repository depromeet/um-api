package com.depromeet.um.api.service;

import com.depromeet.um.api.dto.ChatRoomInfo;
import com.depromeet.um.api.dto.ChatRoomRequest;
import com.depromeet.um.api.dto.ChatRoomsResponse;
import com.depromeet.um.api.model.ChatRoom;
import com.depromeet.um.api.model.User;
import com.depromeet.um.api.util.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final UserSessionService userSessionService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public ChatService(UserSessionService userSessionService, ChatRoomService chatRoomService,
                       UserService userService) {
        this.userSessionService = userSessionService;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    public ChatRoomsResponse getChatRooms() {
        List<Long> chatRoomIds = userSessionService.getCurrentUser().getChatRoomIds();
        List<ChatRoom> chatRooms = chatRoomService.findAllByChatRoomIds(chatRoomIds);

        List<ChatRoomInfo> chatRoomInfos = chatRooms.stream()
                .map(chatRoom -> ChatRoomInfo.builder()
                        .chatRoomId(chatRoom.getId())
                        .brokerChannel(chatRoom.getBrokerChannel())
                        .joinUserIds(chatRoom.getJoinUserIds())
                        .build()).collect(Collectors.toList());

        return ChatRoomsResponse.builder()
                .chatRoomInfos(chatRoomInfos)
                .build();
    }

    public ChatRoomInfo createChatRoom(ChatRoomRequest chatRoomRequest) {
        Long chatRoomId = chatRoomService.generateSequenceId();
        List<Long> joinUserIds = Lists.newArrayList(chatRoomRequest.getJoinUserIds());
        joinUserIds.add(userSessionService.getCurrentUserSession().getId());
        ChatRoom chatRoom = ChatRoom.builder()
                .id(chatRoomId)
                .brokerChannel(StringUtils.formatBrokerChannel(chatRoomId))
                .joinUserIds(joinUserIds)
                .build();
        chatRoomService.save(chatRoom);
        updateUserChatRooms(joinUserIds, chatRoomId);

        return ChatRoomInfo.builder()
                .chatRoomId(chatRoom.getId())
                .brokerChannel(chatRoom.getBrokerChannel())
                .joinUserIds(chatRoom.getJoinUserIds())
                .build();
    }

    private void updateUserChatRooms(List<Long> joinUserIds, Long chatRoomId) {
        List<User> users = userService.findAllByUserIds(joinUserIds);
        users.forEach(user -> user.addChatRoomId(chatRoomId));
        userService.saveAll(users);
    }
}
