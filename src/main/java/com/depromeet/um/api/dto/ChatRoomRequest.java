package com.depromeet.um.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatRoomRequest {
    @NotNull
    private List<Long> joinUserIds;
}
