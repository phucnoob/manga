package uet.ppvan.mangareader.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Value mapper to serialize and deserialize JSON
 */
public class ValueMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String objectAsJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonAsObject(Class<T> type, String json) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
