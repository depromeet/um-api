package com.depromeet.um.api.util;

public class StringUtils {
    public static final String UM_PREFIX = "@UM@";
    public static final String PRIVATE_CHAT_PREFIX = "@PRIVATE@";
    public static final String CHAT_ROOM_PREFIX = "/chat/room";
    public static final String BROKER_PREFIX = "@BROKER@";
    public static final String EMPTY_STRING = "";

    public static String formatBrokerChannel(Long chatRoomId) {
        return CHAT_ROOM_PREFIX + "/" + BROKER_PREFIX + chatRoomId + "@" + System.currentTimeMillis();
    }
}
