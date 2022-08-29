package com.revature.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
