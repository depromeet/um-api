package com.depromeet.um.api.service;

import com.depromeet.um.api.domain.ChatRoomService;
import com.depromeet.um.api.domain.UserChatRoomService;
import com.depromeet.um.api.domain.UserService;
import com.depromeet.um.api.domain.model.UserChatRoom;
import com.depromeet.um.api.dto.ChatRoomRequest;
import com.depromeet.um.api.dto.ChatRoomType;
import com.depromeet.um.api.dto.ChatRoomsResponse;
import com.depromeet.um.api.domain.model.ChatRoom;
import com.depromeet.um.api.util.EnumUtils;
import com.depromeet.um.api.util.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final UserSessionService userSessionService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final UserChatRoomService userChatRoomService;

    public ChatService(UserSessionService userSessionService, ChatRoomService chatRoomService,
                       UserService userService, UserChatRoomService userChatRoomService) {
        this.userSessionService = userSessionService;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
        this.userChatRoomService = userChatRoomService;
    }

    public ChatRoomsResponse getChatRooms() {
        UserChatRoom userChatRoom =
                userChatRoomService.findByUserId(userSessionService.getCurrentUserSession().getId());
        List<Long> chatRoomIds = Lists.newArrayList(userChatRoom.getDirectChatRoomIds());
        chatRoomIds.addAll(userChatRoom.getGroupChatRoomIds());
        List<ChatRoom> chatRooms = chatRoomService.findAllByChatRoomIds(chatRoomIds);

        return ChatRoomsResponse.builder()
                .chatRoomInfos(chatRooms)
                .build();
    }

    public ChatRoom createChatRoom(ChatRoomRequest chatRoomRequest) {
        List<Long> requestJoinUserIds = Lists.newArrayList(chatRoomRequest.getJoinUserIds());
        Long userId = userSessionService.getCurrentUserSession().getId();
        requestJoinUserIds.add(userId);
        List<Long> joinUserIds = requestJoinUserIds.stream().distinct().collect(Collectors.toList());

        if (userService.countByUserIds(joinUserIds) != (long) joinUserIds.size()) {
            throw new IllegalArgumentException("Undefined user id include");
        }
        ChatRoomType chatRoomType = EnumUtils.calcChatRoomType(joinUserIds.size());
        List<UserChatRoom> userChatRooms = userChatRoomService.findAllByUserIds(joinUserIds);
        // document 가 없는 유저가 존재 == 채팅방이 없는 유저가 존재 업데이트 누락 방지
        if (userChatRooms.size() != joinUserIds.size()) {
            joinUserIds.forEach(userChatRoomService::saveIfNotExist);
            userChatRooms = userChatRoomService.findAllByUserIds(joinUserIds);
        }
        List<UserChatRoom> finalUserChatRooms = userChatRooms;
        return existDirectChatRoom(userChatRooms, chatRoomType)
                .orElseGet(() -> saveNewUserChatRooms(finalUserChatRooms, joinUserIds, chatRoomType));
    }

    private ChatRoom saveNewUserChatRooms(List<UserChatRoom> userChatRooms, List<Long> joinUserIds, ChatRoomType chatRoomType) {
        Long chatRoomId = chatRoomService.generateSequenceId();
        userChatRooms.forEach(userChatRoom -> userChatRoom.addChatRoomId(chatRoomId, chatRoomType));
        userChatRoomService.saveAll(userChatRooms);
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomType(chatRoomType)
                .id(chatRoomId)
                .brokerChannel(StringUtils.formatBrokerChannel(chatRoomId))
                .joinUserIds(joinUserIds)
                .build();
        chatRoomService.save(chatRoom);
        return chatRoom;
    }

    private Optional<ChatRoom> existDirectChatRoom(List<UserChatRoom> userChatRooms, ChatRoomType chatRoomType) {
        // direct 면 2명 이다. 하지만 한번더 체크
        if (chatRoomType == ChatRoomType.DIRECT || userChatRooms.size() == 2) {
            List<Long> tempUserChatRoomIds = Lists.newArrayList(userChatRooms.get(0).getDirectChatRoomIds());
            List<Long> tempPeerChatRoomIds = Lists.newArrayList(userChatRooms.get(1).getDirectChatRoomIds());
            tempUserChatRoomIds.retainAll(tempPeerChatRoomIds);
            if (!tempUserChatRoomIds.isEmpty()) {
                try {
                    return Optional.ofNullable(chatRoomService.findByChatRoomId(tempUserChatRoomIds.get(0)));
                } catch (IllegalArgumentException e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

}
