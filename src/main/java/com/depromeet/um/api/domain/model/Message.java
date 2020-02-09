package com.depromeet.um.api.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Document
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Message {
    /**
     * id generated 를 위한 key string
     */
    @Transient
    public static final String SEQUENCE_NAME = "messageSequence";

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NotNull
    private Long authorId;
    @NotNull
    private String brokerChannel;
    @NotNull
    private Long chatRoomId;
    @NotNull
    private Long sequenceNo;
    @NotNull
    private String content;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
