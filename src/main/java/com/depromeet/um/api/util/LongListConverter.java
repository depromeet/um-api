package com.depromeet.um.api.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> longList) {
        if (null == longList) return StringUtils.EMPTY_STRING;
        StringBuilder stringBuilder = new StringBuilder();

        longList.forEach(
                it -> stringBuilder.append(it).append(",")
        );
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    @Override
    public List<Long> convertToEntityAttribute(String string) {
        if (string.isEmpty()) return Lists.newArrayList();
        return Stream.of(string.split(SPLIT_CHAR))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}

