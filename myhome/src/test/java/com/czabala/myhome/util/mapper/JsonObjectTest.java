package com.czabala.myhome.util.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.util.Map.of;

class JsonObjectTest {

    @Test
    void jsonMePlease_shouldReturnJsonString_whenMapIsNotEmpty() {
        Map<String, String> jsonMap = of("key1", "value1", "key2", "value2", "key3", "value3");
        assertEquals("{\"key1\": \"value1\",\"key2\": \"value2\",\"key3\": \"value3\"}", new JsonObject().jsonMePlease(jsonMap));
    }

    @Test
    void jsonMePlease_shouldReturnEmptyJsonString_whenMapIsEmpty() {
        Map<String, String> jsonMap = Collections.emptyMap();
        assertEquals("{}", new JsonObject().jsonMePlease(jsonMap));
    }

    @Test
    void jsonMsgResponse_shouldReturnResponseEntity_withStatusAndMessage() {
        ResponseEntity<?> responseEntity = JsonObject.jsonMsgResponse(200, "message");
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("{\"message\": \"message\"}", responseEntity.getBody());
    }

    @Test
    void toString_shouldReturnJsonString_withKeyAndValue() {
        assertEquals("{\"key\": \"value\"}", new JsonObject("key", "value").toString());
    }

    @Test
    void toString_shouldReturnJsonString_withMessageAndValue_whenOnlyValueIsProvided() {
        assertEquals("{\"message\": \"value\"}", new JsonObject("value").toString());
    }
}