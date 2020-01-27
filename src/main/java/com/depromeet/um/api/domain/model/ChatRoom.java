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
public class ChatRoom {

    /**
     * id generated 를 위한 key string
     */
    @Transient
    public static final String SEQUENCE_NAME = "chatRoomSequence";
    /**
     * epoch time + uuid 조합으로 이루어진 유일한 id 이다.
     */
    @Id
    @NotNull
    private Long chatRoomId;
    @NotNull
    private ChatRoomType chatRoomType;
    @NotNull
    private String brokerChannel;
    @NotNull
    private List<Long> joinUserIds;

}
