package com.czabala.myhome.util.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a JSON object with a key and a value.
 * It provides methods to convert a map to a JSON string and to create a JSON response entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JsonObject {
    private String jsonKey;
    private String jsonValue;

    /**
     * Constructor that sets the key to "message" and the value to the provided argument.
     *
     * @param jsonValue the value to be set for the JSON object
     */
    public JsonObject(String jsonValue) {
        this.jsonKey = "message";
        this.jsonValue = jsonValue;
    }

    /**
     * Converts a map to a JSON string.
     *
     * @param map the map to be converted
     * @return a JSON string representing the map
     */
    public static String jsonMePlease(Map<String, String> map) {
        return map.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"")
                .collect(Collectors.joining(",", "{", "}"));
    }

    /**
     * Creates a JSON String response entity with the provided status and message.
     *
     * @param status  the status of the response
     * @param message the message to be included in the response
     * @return a ResponseEntity with the status and a JSON body containing the message
     */
    public static ResponseEntity<String> jsonMsgResponse(int status, String message) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new JsonObject(message).toString());
    }

    /**
     * Converts this JSON object to a string.
     *
     * @return a string representation of this JSON object
     */
    @Override
    public String toString() {
        return "{\"" + jsonKey + "\": \"" + jsonValue + "\"}";
    }
}