package com.elmagmo3a.java.playstation.util;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    
    @Value("${converter-split-char}")
	private String splitChar;

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(splitChar, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(splitChar));
    }
}