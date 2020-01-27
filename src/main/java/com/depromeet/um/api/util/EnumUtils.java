package com.depromeet.um.api.util;

import com.depromeet.um.api.dto.ChatRoomType;

import static com.depromeet.um.api.dto.ChatRoomType.DIRECT;
import static com.depromeet.um.api.dto.ChatRoomType.GROUP;

public class EnumUtils {
    public static ChatRoomType calcChatRoomType(int count) {
        if(count == 2) return DIRECT;
        else if (count > 2) return GROUP;
        else throw new IllegalArgumentException();
    }
}
