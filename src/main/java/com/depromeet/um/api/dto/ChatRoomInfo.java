package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatRoomInfo {
    @NotNull
    private Long chatRoomId;
    @NotNull
    private String brokerChannel;
    @NotNull
    private List<Long> joinUserIds;
}
