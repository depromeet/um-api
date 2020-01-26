package com.depromeet.um.api.model;

import com.depromeet.um.api.dto.LoginType;
import com.depromeet.um.api.util.LongListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String umId;

    @Embedded
    private UserName userName;

    @Nullable
    private String publicEmail;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LoginType loginType;

    @Lob
    @Convert(converter = LongListConverter.class)
    private List<Long> chatRoomIds;

    public void addChatRoomId(Long chatRoomId) {
        this.chatRoomIds.add(chatRoomId);
    }

    public void removeChatRoomId(Long chatRoomId) {
        this.chatRoomIds.remove(chatRoomId);
    }
}