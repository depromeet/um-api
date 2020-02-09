package com.depromeet.um.api.util;

import com.google.common.collect.Sets;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> stringSet) {
        if (stringSet == null || stringSet.isEmpty()) {
            return "";
        }
        return String.join(SPLIT_CHAR, stringSet);
    }

    @Override
    public Set<String> convertToEntityAttribute(String string) {
        if (string == null || string.isEmpty()) {
            return Sets.newHashSet();
        }
        return Sets.newHashSet(string.split(SPLIT_CHAR));
    }
}

