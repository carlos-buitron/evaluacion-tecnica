package com.microservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Converters {

    private Converters() {
        throw new IllegalStateException(this.getClass().toString());
    }

    public static <T> T convertToGeneric(Object obj, Class<T> responseType) {
        return new ObjectMapper().convertValue(obj, responseType);
    }
}
