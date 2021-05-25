package com.game.bowling.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.game.bowling.model.BowlingRollRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static <T> List<BowlingRollRequest> readBowlingRollRequest(String filePath, TypeReference<List<BowlingRollRequest>> clazz) throws IOException {
        return objectMapper.readValue(new File("src/test/resources" + filePath), clazz);
    }
    public static <T> T readJson(String filePath, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File("src/test/resources" + filePath), clazz);
    }
}

