package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.ChatRoom;
import com.depromeet.um.api.domain.model.UserChatRoom;
import com.depromeet.um.api.domain.repository.UserChatRoomRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.List;

@Service
public class UserChatRoomService {
    private final UserChatRoomRepository userChatRoomRepository;

    public UserChatRoomService(UserChatRoomRepository userChatRoomRepository) {
        this.userChatRoomRepository = userChatRoomRepository;
    }

    public UserChatRoom findByUserId(Long userId) {
        return userChatRoomRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<UserChatRoom> findAllByUserIds(List<Long> userIds) {
        return (List<UserChatRoom>) userChatRoomRepository.findAllById(userIds);
    }

    public boolean isExistDirectChatRoom(Long userId, Long chatRoomId) {
        return userChatRoomRepository.existsByUserIdAndDirectChatRoomIdsIn(userId, chatRoomId);
    }

    public UserChatRoom save(UserChatRoom userChatRoom) {
        return userChatRoomRepository.save(userChatRoom);
    }

    public void saveAll(List<UserChatRoom> userChatRooms) {
        userChatRoomRepository.saveAll(userChatRooms);
    }

    public void saveIfNotExist(Long userId) {
        if (userChatRoomRepository.existsById(userId)) return;
        userChatRoomRepository.save(UserChatRoom.builder()
                .userId(userId)
                .directChatRoomIds(Lists.newArrayList())
                .groupChatRoomIds(Lists.newArrayList())
                .build());
    }
}
