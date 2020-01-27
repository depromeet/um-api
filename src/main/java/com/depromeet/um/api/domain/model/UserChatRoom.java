package com.depromeet.um.api.domain.model;

import com.depromeet.um.api.dto.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class UserChatRoom {

    /**
     * userId 와 매핑된다
     */
    @Id
    @NotNull
    private Long userId;
    @NotNull
    private List<Long> directChatRoomIds;
    @NotNull
    private List<Long> groupChatRoomIds;

    public void addChatRoomId(Long chatRoomId, ChatRoomType chatRoomType) {
        switch (chatRoomType) {
            case DIRECT:
                this.directChatRoomIds.add(chatRoomId);
                break;
            case GROUP:
                this.groupChatRoomIds.add(chatRoomId);
                break;
        }
    }

    public void removeDirectChatRoomId(Long chatRoomId, ChatRoomType chatRoomType) {
        switch (chatRoomType) {
            case DIRECT:
                this.directChatRoomIds.remove(chatRoomId);
                break;
            case GROUP:
                this.groupChatRoomIds.remove(chatRoomId);
                break;
        }
    }
}
